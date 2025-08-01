package com.futbol.Fantasy.utils;

import com.futbol.Fantasy.model.Footballer;
import com.futbol.Fantasy.model.Team;
import com.futbol.Fantasy.service.FootballerService;
import com.futbol.Fantasy.service.TeamService;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class WebScraper {
    private final TeamService teamService;
    private final FootballerService footballerService;

    @Autowired
    public WebScraper(TeamService teamService, FootballerService footballerService) {
        this.teamService = teamService;
        this.footballerService = footballerService;
    }

    public void getTeamsAndFootballersAndSave() {
        System.setProperty("webdriver.edge.driver", "C:/Users/domin/OneDrive/Escritorio/FutbolFantasy/FutbolFantasy/Fantasy/Fantasy/src/main/resources/edgedriver_win64/msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        WebDriver driver = new EdgeDriver(options);

        try {
            teamService.deleteAll();
            footballerService.deleteAll();

            driver.get("https://www.laliga.com/laliga-easports/clubes");
            Thread.sleep(2000);


            List<WebElement> equipos = driver.findElements(By.cssSelector(".styled__ItemContainer-sc-fyva03-1"));

            for (int i = 0; i < equipos.size(); i++) {
                try {

                    equipos = driver.findElements(By.cssSelector(".styled__ItemContainer-sc-fyva03-1"));
                    WebElement equipo = equipos.get(i);

                    String nombre = equipo.findElement(By.cssSelector("h2")).getText();
                    String urlTeam = equipo.findElement(By.cssSelector("a")).getAttribute("href") + "/plantilla";

                    driver.get(urlTeam);
                    Thread.sleep(2000);

                    String fotoUrl = driver.findElement(By.cssSelector("img.styled__ImgShield-sc-1opls7r-1")).getAttribute("src");
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Foto: " + fotoUrl);

                    Team team = new Team();
                    team.setName(nombre);
                    team.setPhoto(fotoUrl);
                    teamService.insert(team);

                    List<WebElement> jugadores = driver.findElements(By.cssSelector("div.styled__SquadPlayerCardContainer-sc-148d0nz-0.duzYxA"));
                    System.out.println("Número de jugadores encontrados: " + jugadores.size());

                    for (WebElement jugador : jugadores) {
                        try {
                            String nombreJugador = jugador.findElement(By.cssSelector(".styled__TextStyled-sc-1mby3k1-0.dtQzta")).getText();
                            String posicion = jugador.findElement(By.cssSelector(".styled__TextStyled-sc-1mby3k1-0.bfGfhz")).getText();

                            System.out.println("Nombre Jugador: " + nombreJugador);
                            System.out.println("Posición: " + posicion);

                            List<String> rols = List.of("PORTERO", "DEFENSA", "CENTROCAMPISTA", "DELANTERO");
                            if (rols.contains(posicion)) {
                                Footballer footballer = new Footballer();
                                footballer.setName(nombreJugador);
                                footballer.setRol(posicion);
                                footballer.setTeam(team);

                                try {
                                    System.out.println("Intentando guardar el futbolista: " + footballer);
                                    footballerService.insert(footballer);
                                } catch (Exception e) {
                                    System.out.println("Error al insertar el jugador: " + e.getMessage());
                                }
                            }

                        } catch (StaleElementReferenceException e) {
                            System.out.println("Elemento obsoleto encontrado, ignorando este jugador.");
                        }
                    }

                    driver.get("https://www.laliga.com/laliga-easports/clubes");
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("Error procesando el equipo: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
