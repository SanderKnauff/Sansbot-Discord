package nl.imine.discord.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.logic.Channel;
import nl.imine.discord.logic.Member;

import java.time.LocalDateTime;
import java.util.List;

public class Guild {

    @JsonProperty("voice_states")
    private List<VoiceState> voiceStates;
    @JsonProperty("verification_level")
    private int verificationLevel;
    private boolean unavailable;
    @JsonProperty("system_channel_id")
    private String systemChannelId;
    private Splash splash;
    private List<Role> roles;
    private String region;
    private List<Presence> presences;
    @JsonProperty("owner_id")
    private String ownerId;
    private String name;
    @JsonProperty("mfa_level")
    private int mfaLevel;
    private List<Member> members;
    @JsonProperty("member_count")
    private int memberCount;
    private boolean large;
    private LocalDateTime joinedAt;
    private String id;
    private String icon;
    private List<Feature> features;
    @JsonProperty("explicit_content_filter")
    private int explicitContentFilter;
    private List<Emoji> emojis;
    @JsonProperty("default_messages_notification")
    private int defaultMessageNotifications;
    private List<Channel> channels;
    @JsonProperty("application_id")
    private String applicationId;
    @JsonProperty("afk_timeout")
    private int afkTimeout;
    @JsonProperty("afk_channel_id")
    private String afkChannelId;

    public Guild() {
    }

    public Guild(List<VoiceState> voiceStates, int verificationLevel, boolean unavailable, String systemChannelId, Splash splash, List<Role> roles, String region, List<Presence> presences, String ownerId, String name, int mfaLevel, List<Member> members, int memberCount, boolean large, LocalDateTime joinedAt, String id, String icon, List<Feature> features, int explicitContentFilter, List<Emoji> emojis, int defaultMessageNotifications, List<Channel> channels, String applicationId, int afkTimeout, String afkChannelId) {
        this.voiceStates = voiceStates;
        this.verificationLevel = verificationLevel;
        this.unavailable = unavailable;
        this.systemChannelId = systemChannelId;
        this.splash = splash;
        this.roles = roles;
        this.region = region;
        this.presences = presences;
        this.ownerId = ownerId;
        this.name = name;
        this.mfaLevel = mfaLevel;
        this.members = members;
        this.memberCount = memberCount;
        this.large = large;
        this.joinedAt = joinedAt;
        this.id = id;
        this.icon = icon;
        this.features = features;
        this.explicitContentFilter = explicitContentFilter;
        this.emojis = emojis;
        this.defaultMessageNotifications = defaultMessageNotifications;
        this.channels = channels;
        this.applicationId = applicationId;
        this.afkTimeout = afkTimeout;
        this.afkChannelId = afkChannelId;
    }

    public List<VoiceState> getVoiceStates() {
        return voiceStates;
    }

    public void setVoiceStates(List<VoiceState> voiceStates) {
        this.voiceStates = voiceStates;
    }

    public int getVerificationLevel() {
        return verificationLevel;
    }

    public void setVerificationLevel(int verificationLevel) {
        this.verificationLevel = verificationLevel;
    }

    public boolean isUnavailable() {
        return unavailable;
    }

    public void setUnavailable(boolean unavailable) {
        this.unavailable = unavailable;
    }

    public String getSystemChannelId() {
        return systemChannelId;
    }

    public void setSystemChannelId(String systemChannelId) {
        this.systemChannelId = systemChannelId;
    }

    public Splash getSplash() {
        return splash;
    }

    public void setSplash(Splash splash) {
        this.splash = splash;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMfaLevel() {
        return mfaLevel;
    }

    public void setMfaLevel(int mfaLevel) {
        this.mfaLevel = mfaLevel;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public boolean isLarge() {
        return large;
    }

    public void setLarge(boolean large) {
        this.large = large;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public int getExplicitContentFilter() {
        return explicitContentFilter;
    }

    public void setExplicitContentFilter(int explicitContentFilter) {
        this.explicitContentFilter = explicitContentFilter;
    }

    public List<Emoji> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<Emoji> emojis) {
        this.emojis = emojis;
    }

    public int getDefaultMessageNotifications() {
        return defaultMessageNotifications;
    }

    public void setDefaultMessageNotifications(int defaultMessageNotifications) {
        this.defaultMessageNotifications = defaultMessageNotifications;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public int getAfkTimeout() {
        return afkTimeout;
    }

    public void setAfkTimeout(int afkTimeout) {
        this.afkTimeout = afkTimeout;
    }

    public String getAfkChannelId() {
        return afkChannelId;
    }

    public void setAfkChannelId(String afkChannelId) {
        this.afkChannelId = afkChannelId;
    }
}
