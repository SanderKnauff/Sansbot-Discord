package nl.imine.discord.voice.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.imine.discord.event.EventDispatcher;
import nl.imine.discord.event.EventHandler;
import nl.imine.discord.event.Listener;
import nl.imine.discord.event.gateway.GuildCreatedEvent;
import nl.imine.discord.event.gateway.VoiceServerUpdateEvent;
import nl.imine.discord.event.gateway.VoiceStateUpdateEvent;
import nl.imine.discord.event.voice.VoiceSocketConnectedEvent;
import nl.imine.discord.gateway.GatewaySocket;
import nl.imine.discord.gateway.messages.VoiceStateUpdateMessage;
import nl.imine.discord.gateway.messages.data.VoiceStateUpdateData;
import nl.imine.discord.logic.Channel;
import nl.imine.discord.model.Guild;
import nl.imine.vaccine.annotation.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class VoiceEventListener implements Listener {

    private static final Logger logger = LoggerFactory.getLogger(VoiceEventListener.class);

    private final VoiceSocket voiceSocket;
    private final EventDispatcher eventDispatcher;
    private final GatewaySocket gatewaySocket;
    private ObjectMapper objectMapper;

    public VoiceEventListener(VoiceSocket voiceSocket, EventDispatcher eventDispatcher, GatewaySocket gatewaySocket, ObjectMapper objectMapper) {
        this.voiceSocket = voiceSocket;
        this.eventDispatcher = eventDispatcher;
        this.gatewaySocket = gatewaySocket;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void postConstruct() {
        eventDispatcher.registerListener(this);
    }

    @EventHandler
    public void onGuildCreate(GuildCreatedEvent guildCreatedEvent) throws Exception{
        Guild guild = guildCreatedEvent.getGuild();
        Optional<Channel> oVoiceChannel = guild.getChannels().stream()
                .filter(channel -> channel.getType().equals(Channel.ChannelType.GUILD_VOICE))
                .findFirst();
        if(oVoiceChannel.isPresent()) {
            Channel channel = oVoiceChannel.get();
            VoiceStateUpdateData voiceStateUpdateData = new VoiceStateUpdateData(guild.getId(),
                    channel.getId(),
                    false,
                    false);
            voiceSocket.setGuildId(guild.getId());
            gatewaySocket.getGatewaySocketAdapter().getSession().getRemote()
                    .sendString(objectMapper.writeValueAsString(new VoiceStateUpdateMessage(0, voiceStateUpdateData)));
        } else {
            logger.error("No voice channel found, aborting connection attempt to voice servers");
        }
    }

    @EventHandler
    public void onVoiceServerUpdate(VoiceServerUpdateEvent voiceServerUpdateEvent) {
        logger.info("{}", voiceServerUpdateEvent.getVoiceServer());
        voiceSocket.setVoiceToken(voiceServerUpdateEvent.getVoiceServer().getToken());
        voiceSocket.setEndpoint(voiceServerUpdateEvent.getVoiceServer().getEndpoint());
    }

    @EventHandler
    public void onVoiceStateUpdate(VoiceStateUpdateEvent voiceStateUpdateEvent) {
        logger.info("{}", voiceStateUpdateEvent.getVoiceState());
        voiceSocket.setUserId(voiceStateUpdateEvent.getVoiceState().getUserId());
        voiceSocket.setSessionId(voiceStateUpdateEvent.getVoiceState().getSessionId());
    }

    @EventHandler
    public void onVoiceSocketConnected(VoiceSocketConnectedEvent voiceSocketConnectedEvent) {
        voiceSocket.sendVoiceIdentifyMessage();
    }
}
