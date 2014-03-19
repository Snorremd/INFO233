package game.util;

import game.entity.AliasNotRegisteredException;
import game.entity.TileFactory;
import game.entity.TileLevel;
import game.entity.TileNotRegisteredException;
import game.entity.tiles.IllegalTileException;
import game.gfx.SpriteLoader;

public interface ResourceLoader {
	/**
	 * Returnerer et brett av typen TileLevel. I hvilken grad en burde ha TileLevel og ikke Level kan diskuteres.
	 * TODO: Vi burde kanskje hatt Level her istedenfor TileLevel?
	 * @param name navnet på brettet som skal lastes inn. I CSV-filen er det i kolonnen "name". I SQL-databasen må dere definere navnet selv.
	 * @return en TileLevel instans. Det er ikke garantert at to kall til getLevel med de samme parametrene vil gi det samme objektet (du kan få samme objektet, men må ikke), men det er garantert at de vil være equal. Skal aldri returnere null
	 * @throws LevelNotFoundException Dersom brettet ikke er registrert i loaderen.
	 * @throws TileNotRegisteredException Dersom brettet spesifiserer en Tile som loaderen ikke vet hvordan den skal representere, dvs. et lang-tilenavn, ikke en forkortelse.
	 * @throws IllegalTileException Dersom en Tile ikke er lovlig plassert.
	 * @throws AliasNotRegisteredException Dersom en av forkortelsene (et alias) i brettfilen ikke er registrert.
	 * Merk at standardutgaven bruker std.out til å logge hva den laster inn for å gi dere en viss oversikt over hva den vet om.
	 */
	public TileLevel getLevel(String name) throws LevelNotFoundException, TileNotRegisteredException, IllegalTileException, AliasNotRegisteredException;
	
	/**
	 * Returnerer et brett med et gitt nummer.
	 * @param number nummeret til brettet. Tenk level 1, level 2, og så videre. Dette er i kolonnen level i adventure.csv filen. Hvordan dere gjør det i SQL-løsningen er opp til dere.
	 * @return en TileLevel instans. To kall til getLevel med de samme parametrene skal gi like objekter, og kan, men trenger ikke gi det samme objektet. Skal aldri returnere null.
	 * @throws LevelNotFoundException Dersom brettet ikke er registrert i loaderen.
	 * @throws TileNotRegisteredException Dersom brettet spesifiserer en Tile som loaderen ikke vet hvordan den skal representere, dvs. et lang-tilenavn, ikke en forkortelse.
	 * @throws IllegalTileException Dersom en Tile ikke er lovlig plassert.
	 * @throws AliasNotRegisteredException Dersom en av forkortelsene (et alias) i brettfilen ikke er registrert.
	 * Merk at standardutgaven bruker std.out til å logge hva den laster inn for å gi dere en viss oversikt over hva den vet om.
	 */
	public TileLevel getLevel(int number) throws LevelNotFoundException, TileNotRegisteredException, IllegalTileException, AliasNotRegisteredException;
	
	/**
	 * Returnerer en registrert spriteloader til bruk av andre biter av systemet.
	 * @param name navnet til spriteloaderen. En del navn er magiske, dvs. at de har en spesiell betydning:
	 * <table>
	 *     <tr><th>Navn</th><th>Betydning</th></tr>
	 *     <tr><td>tile</td><td>spriteloaderen som vet om tiles</td>
	 *     <tr><td>player</td><td>spriteloaderen som vet om spilleren</td></tr>
	 *     <tr><td>monstre</td><td>spriteloaderen som vet om de standard statiske monstrene</td></tr>
	 * </table>
	 * @return Spriteloaderen. Aldri null. Dersom du kaller denne metoden flere ganger med samme argumenter <i>er du garantert å få samme objekt</i>.
	 *  Dvs. at loader.getSpriteLoader(etNavn) == loader.getSpriteLoader(etNavn) alltid vil holde.
	 *  Derfor må du mellomlagre
	 * @throws SpriteSheetNotFoundException dersom det ikke finnes en spriteloader med det navnet spesifisert.
	 */
	public SpriteLoader getSpriteLoader (String name) throws SpriteSheetNotFoundException;

	/**
	 * Tilefactoryen her er den som brukes internt hvis det blir ønskelig. (Td. om dere vil laste brett som ikke er registrert i ResourceLoader objektet.
	 * @return en TileFactory instans. Unikhet garanteres ikke. Det er derimot garantert at alle registrerte tiles og aliaser som er registrert i ResourceLoaderen er tilgjengelige i dette objektet.
	 */
	public TileFactory getTileFactory();
	
	/**
	 * Antall brett som er lastet inn i dette ResourceLoader objektet. 
	 * @return et tall t som er 0 eller større. Det kreves ingen garanti for at brettene fra 1-t skal eksistere, men game.controller.Game later som en slik garanti eksisterer.
	 * Derfor må dere enten endre klassen Game slik at den ikke er avhengig av en slik garanti, eller la deres implementasjon garantere dette.
	 * (Det siste er veldig, veldig enkelt vha SQL. og anbefales at dere bruker)
	 */
	public int getNumLevels();
}
