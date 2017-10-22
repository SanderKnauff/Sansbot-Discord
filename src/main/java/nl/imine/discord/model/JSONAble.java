package nl.imine.discord.model;

import org.json.simple.JSONObject;

public interface JSONAble<T> {

	JSONObject toJSON();
}
