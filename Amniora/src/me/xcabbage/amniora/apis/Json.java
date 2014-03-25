package me.xcabbage.amniora.apis;

import net.enigmablade.riotapi.RiotApi;
import net.enigmablade.riotapi.constants.Region;
import net.enigmablade.riotapi.constants.Season;
import net.enigmablade.riotapi.exceptions.RiotApiException;
import net.enigmablade.riotapi.types.Summoner;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

public class Json {

	public final String adress = "https://prod.api.pvp.net/api/";
	public final String apiKey = "9407a2da-aac3-4240-8ce5-e1758ee4bb1d";
	RiotApi api;
	Summoner profile;

	public Json() {
		JsonFactory factory = new JsonFactory();

		factory.enable(JsonParser.Feature.ALLOW_COMMENTS);
		api = new RiotApi(apiKey);

	}

	public boolean setSummoner(String name) {
		try {
			profile = api.getSummoner(Region.EUNE, name);
			return true;
		} catch (RiotApiException e) {

			e.printStackTrace();

			return false;
		}

	}

	public String getLevel() {
		try {
			return String.valueOf(api.getSummonerMethod()
					.getSummonerByName(Region.EUNE, profile.getName())
					.getSummonerLevel());
		} catch (RiotApiException e) {

			e.printStackTrace();
			return "Error fetching data; Check the logs.";
		}
	}

	public boolean setSummoner(String name, Region region) {

		try {
			profile = api.getSummoner(region, name);
			return true;
		} catch (RiotApiException e) {

			e.printStackTrace();

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void dump() {
		try {
			System.out.println(api.getStatsMethod().getStatSummaries(
					profile.getRegion(), profile.getId(), Season.SEASON_4));
		} catch (RiotApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
