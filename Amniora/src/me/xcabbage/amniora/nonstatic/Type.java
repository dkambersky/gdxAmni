package me.xcabbage.amniora.nonstatic;

import me.xcabbage.amniora.GameAmn;

/**
 * The Type.java enum where the different tile types of the game are defined and
 * tweaked.
 * 
 * @author xCabbage [github.com/xcabbage]
 * 
 * @info for the Amniora project [github.com/xcabbage/amniora] created 14. 8.
 *       2014 16:23:22
 */

public enum Type {

	// Normal tiles w/ added starter tiles
	Normal(), Starter1(1), Starter2(2),

	// Special tiles
	CombatTemple(StructureType.COMBAT_TEMPLE), RichEarth(
			StructureType.RICH_EARTH),

	// Unique tiles
	FarsightPlanes(StructureType.FAR_SIGHT_PLANES), ExcavationLab(
			StructureType.EXCAVATION_TECH_LAB), VanguardShrine(
			StructureType.VANGUARD_SHRINE), RaiderShrine(
			StructureType.RAIDER_SHRINE);

	final int captureTime;
	final StructureType structure;
	final String name;

	// unused functionality - render images
	final String RelPath = "resources/";
	String image = "";

	// normal tile
	Type() {
		name = "Base tile";
		structure = StructureType.NONE;
		captureTime = 20;

		// unused - render
		this.image = RelPath.concat(image).concat(".png");

	}

	// starter sped-up tiles
	Type(int starterTypeLevel) {
		if (starterTypeLevel == 1) {
			name = "Starter tile I";
			captureTime = 10;
		} else if (starterTypeLevel == 2) {
			name = "Starter tile II";
			captureTime = 15;
		} else {
			// if invalid parameter supplied, create a retarded tile
			GameAmn.error("Invalid tile type supplied: " + starterTypeLevel);
			name = "Magical tile of Retardedness";
			captureTime = 20;
		}

		structure = StructureType.NONE;

		// unused - render
		this.image = RelPath.concat(image).concat(".png");

	}

	// special structures
	Type(StructureType type) {
		captureTime = 20;
		structure = type;
		switch (type) {
		// Uniques:
		case FAR_SIGHT_PLANES:
			name = "Planes of Far Sight";
			break;
		case EXCAVATION_TECH_LAB:
			name = "Excavation tech lab";
			break;
		case VANGUARD_SHRINE:
			name = "Shrine of the Vanguard";
			break;
		case RAIDER_SHRINE:
			name = "Shrine of the Raider";
			break;

		// Specials:
		case COMBAT_TEMPLE:
			name = "Combat temple";
			break;

		case RICH_EARTH:
			name = "Rich earth";
			break;

		default:
			// if invalid parameter supplied, create a retarded tile
			GameAmn.error("Invalid tile type supplied: " + type);
			name = "Magical tile of Retardedness";

		}

		// unused - render
		this.image = RelPath.concat(image).concat(".png");

	}
}