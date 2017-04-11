package com.chess.core.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TransformJson {

	public static String createResponseJson(ResponseClient res){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(res);
	}
}
