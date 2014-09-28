package me.xcabbage.amniora.apis;

import me.xcabbage.amniora.GameAmn;
import net.enigmablade.riotapi.RiotApi;
import net.enigmablade.riotapi.constants.Region;
import net.enigmablade.riotapi.constants.Season;
import net.enigmablade.riotapi.exceptions.RiotApiException;
import net.enigmablade.riotapi.types.Summoner;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

public class Json {
	/*
	 * Just a somewhat useless class I used to test out Riot's League of Legends
	 * API
	 */
	public final String adress = "https://prod.api.pvp.net/api/";
	//pls no stealerino ;_;
	public final String apiKey = "4b485ad8-8bcd-4646-9947-1bf95481a03f";
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

			GameAmn.error(e.getStackTrace());

			return false;
		}

	}

	public String getLevel() {
		try {
			return String.valueOf(api.getSummonerApiMethod()
					.getSummonerByName(Region.EUNE, profile.getName())
					.getSummonerLevel());
		} catch (RiotApiException e) {

			GameAmn.error(e.getStackTrace());
			return "Error fetching data; Check the logs.";
		}
	}

	public boolean setSummoner(String name, Region region) {

		try {
			profile = api.getSummoner(region, name);
			System.out.println("Summoner successfully retrieved");
			return true;
		} catch (RiotApiException e) {

			GameAmn.error(e.getStackTrace());

			return false;
		} catch (Exception e) {
			GameAmn.error(e.getStackTrace());
			return false;
		}
	}

	public void dump() {
		try {
			System.out.println(api.getStatsApiMethod().getStatSummaries(
					profile.getRegion(), profile.getId(), Season.SEASON_4));
		} catch (RiotApiException e) {
			// TODO Auto-generated catch block
			GameAmn.error(e.getStackTrace());
		}
	}
}
