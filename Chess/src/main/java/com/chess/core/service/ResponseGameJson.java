package com.chess.core.service;

import com.chess.core.ResponseChessboard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseGameJson {

	public static String createResponseJson(ResponseChessboard res){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(res);
	}
}
