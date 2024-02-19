package tvt23.harjoitus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private Game game;

    //juuri mappaus antaa ohjeet pelin pelaamiseen endpointeilla
    @GetMapping("/")
    public ResponseEntity<?> getInstructions() {
        String instructions = "Muistipelin API-ohjeet:\n" +
                "- Pelin aloitus: POST /api/game/start {\"pairCount\": n}\n" +
                "- Siirron tekeminen: POST /api/game/move {\"card1Index\": 0 - n-1, \"card2Index\": 0 - n-1}\n" +
                "- Pelin tilan tarkastelu: GET /api/game/status\n" +
                "Käytä JSON-muotoista dataa POST-pyynnöissä.";
        return ResponseEntity.ok(instructions);
    }
    
    //alustaa uuden pelin halutulla määrällä pareja
    @PostMapping("/start")
    public ResponseEntity<?> startGame(@RequestBody GameStartRequest request) {
        game = new Game(request.getPairCount());
        return ResponseEntity.ok("Uusi peli aloitettu " + request.getPairCount() + " parilla.");
    }

    //kääntää kaksi valittua korttia ja tarkistaa kortin id:n perusteella ovatko ne parit vai eivät
    @PostMapping("/move")
    public ResponseEntity<?> makeMove(@RequestBody MoveRequest moveRequest) {
        if (game == null) {
            return ResponseEntity.badRequest().body("Peliä ei ole aloitettu.");
        }
        boolean isMatch = game.makeMove(moveRequest.getCard1Index(), moveRequest.getCard2Index());
        if (isMatch) {
            return ResponseEntity.ok("Löysit parin!");
        } else {
            return ResponseEntity.ok("Ei ollut pari. Yritä uudelleen.");
        }
    }

    //näyttää pelin tilanteen löydetyt parit true löytämättömät false, sekä käytettyjen siirtojen määrän ja onko peli vielä kesken.
    @GetMapping("/status")
    public ResponseEntity<?> getGameStatus() {
        if (game == null) {
            return ResponseEntity.badRequest().body("Peliä ei ole aloitettu.");
        }
        return ResponseEntity.ok(game);
    }
}

class MoveRequest {
    private int card1Index;
    private int card2Index;
    public int getCard1Index() {
        return card1Index;
    }
    public void setCard1Index(int card1Index) {
        this.card1Index = card1Index;
    }
    public int getCard2Index() {
        return card2Index;
    }
    public void setCard2Index(int card2Index) {
        this.card2Index = card2Index;
    }
}

class GameStartRequest {
    private int pairCount;

    public int getPairCount() {
        return pairCount;
    }

    public void setPairCount(int pairCount) {
        this.pairCount = pairCount;
    }
}