//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.beans.AuthenticationInformation;
import com.example.data.Game;
import com.example.data.Player;
import com.example.data.Team;
import com.example.data.User_;
import com.example.formdata.FormEvent;
import com.example.formdata.FormPlayer;
import com.example.formdata.FormResult;
import com.example.formdata.LoginFormData;
import com.example.formdata.MenuItem;
import com.example.formdata.StatsData;
import com.example.formdata.TeamForm;

@Controller
public class ProjetoController {

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;

    @Autowired
    EventService eventService;

    @Resource(name = "AuthenticationInformationGenerator")
    private AuthenticationInformation AuthenticationInformation;


    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    //serve para criar um objeto AuthenticationInforamtion
    public AuthenticationInformation AuthenticationInformationGenerator() {
        return new AuthenticationInformation();
    }

    //se o link for so "/"
    @GetMapping("/")
    public String redirect() {
        User_ user = new User_("admin", "admin@gmail.com", "admin", "SedeAdmin", 123456789, true);
        userService.addUser(user);
        //redireciona para o "/home"
        return "redirect:/home";
    }
    
    //se o link for /home
    //Recebe o erro que pode vir do authentication
    @GetMapping("/home")
    public String home(@ModelAttribute("error") String error, Model model) {
        // System.out.println("AuthInfo:\nisAuth: " +
        // this.AuthenticationInformation.getIsAuthenticated() + "\nisAdmin: " +
        // this.AuthenticationInformation.getIsAdmin());

        //dentro do allgames vou por os games todos da BD
        //os jogos vêm do gameService.getAllGames()
        model.addAttribute("allGames", gameService.getAllGames());
        //criar uma lista de objetos MenuItem
        //estes objetos têm uma string(a dizer o que se trata) e um url
        //exemplo:
        //- se tiver login para a string e /login para o url
        //  vai aparecer na página escrito Login e se clicar nesse texto vai levar 
        //  para o /login
        List<MenuItem> menu = new ArrayList<MenuItem>();
        //se a informação diz que não está logado
        if (!this.AuthenticationInformation.getIsAuthenticated()) {
            //cria-se um menu que tenha a string Login e o link para o login
            //adiciona-se à lista de menu
            //isto vai ser usado no html do home depois
            menu.add(new MenuItem("Login", "/login"));
            model.addAttribute("logged", "0");
          
        }
        //se afinal ele está logado 
        else {
            model.addAttribute("logged", "1");
            //Se o error estava vazio coloca-o a 1(não há erro)
            if(error.compareTo("") == 0){
                model.addAttribute("error", "1");
            }
            //Se não o erro já fica a zero
            menu.add(new MenuItem("Logout", "/logout"));
            //e se é admin
            if (this.AuthenticationInformation.getIsAdmin()) {
                //cria-se um menu que tenha as strings abaixo escritas 
                //e o link para cada uma respetivamente
                //adiciona-se à lista de menu
                menu.add(new MenuItem("Create User", "/create-user"));
                menu.add(new MenuItem("Create Team", "/create-team"));
                menu.add(new MenuItem("Edit Game", "/edit-game"));
                menu.add(new MenuItem("Create Player", "/create-player"));
                menu.add(new MenuItem("Edit Player", "/edit-player"));
                menu.add(new MenuItem("Create Game", "/create-game"));
                menu.add(new MenuItem("Edit Game", "/edit-game"));
            }
        }

        //cria-se um menu que tenha a string Show Stats e o link para as stats
        //adiciona-se à lista de menu
        menu.add(new MenuItem("Show Stats", "/show-stats?sort=0"));

        //no final teremos uma lista com elementos do tipo MenuItem
        //onde cada elemento tem uma string e um link associados
        //dentro do menu vou por esta lista que criei 
        //para depois ser passado para o html
        model.addAttribute("menu", menu);


        //retorna a página html do home
        //ou seja leva para a página home
        return "home";
    }

    @GetMapping("/generate-data")
    public String generateData(){
        teamService.generateTeam("Ajax");
        teamService.generateTeam("Portimonense");
        teamService.generateTeam("Benfica");
        teamService.generateTeam("FC Porto");

        return "redirect:/home";
    }

    @GetMapping("/edit-team")
    public String editTeam(Model m){
        if (this.AuthenticationInformation.getIsAdmin()){
        TeamForm tf = new TeamForm();
        m.addAttribute("allTeams", teamService.getAllTeams());
        m.addAttribute("teamForm",tf);
        return "edit-team";
        }else{
            return "redirect:/home";
        }
    }


    @PostMapping("/save-edit-team")
    public String saveEditTeam(@ModelAttribute TeamForm teamform){
        teamService.updateTeam(teamform.getName(), teamform.getId());
        return "redirect:/home";
    }

    @GetMapping("/show-stats")
    public String showStats(@RequestParam(name = "sort", required = true) int sort, Model m){
        List<StatsData> allStats = new ArrayList<>();
        String[] aux = playerService.BestScorer().split(",");
        
        for (Team t: teamService.getAllTeams()) {
            allStats.add(teamService.getStats(t.getName()));
        }

        switch(sort){

           case 1:
           allStats.sort((s1, s2) -> Integer.compare(s1.getVictories(), s2.getVictories()));
           Collections.reverse(allStats);
           break;

           case 2:
           allStats.sort((s1, s2) -> Integer.compare(s1.getDefeats(), s2.getDefeats()));
           Collections.reverse(allStats);
           break;

           case 3:
           allStats.sort((s1, s2) -> Integer.compare(s1.getTied(), s2.getTied()));
           Collections.reverse(allStats);
           break;

           case 4:
           allStats.sort((s1, s2) -> Integer.compare(s1.getTotal(), s2.getTotal()));
           Collections.reverse(allStats);
           break;

           default:
           allStats.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
           break;


        }
        
        

        m.addAttribute("BestScorerName", aux[0]);
        m.addAttribute("BestScorerGoals", aux[1]);
        
        m.addAttribute("allStats", allStats);
        return "stats.html";
    }

    //se o link for /details
    @GetMapping("/details")
    //lembrar ele recebe o id como parâmetro no link, tipo ?id=3
    //será referente ao jogo com id = 3
    public String showDetails(@RequestParam(name = "id", required = true) int id, Model model) {
        //vai procurar o game na BD usando id
        Optional<Game> op = gameService.findById(id);
        
        //se o jogo existir 
        if (op.isPresent()) {
            //dentro do game vou por o game encontrado 
            //para depois ser passado para o html
            model.addAttribute("OrderEvent", gameService.OrderEventOfGame(op.get()));
            model.addAttribute("game", op.get());
            model.addAttribute("resultA",eventService.getScoreTeam(op.get().getTeamA().getName(), op.get().getId()));
            model.addAttribute("resultB",eventService.getScoreTeam(op.get().getTeamB().getName(), op.get().getId()));
            //retorna a página html do details 
            //ou seja leva para a página details do game em questão
            return "details";
        }
        //se não existir
        else {
            //redireciona para o "/home"
            return "redirect:/home";
        }
    }

    //se o link for /login
    @GetMapping("/login")
    public String loginForm(Model model) {
        //dentro do LoginFormData vou por um objeto LoginFormData criado vazio
        //para depois ser passado para o html
        model.addAttribute("LoginFormData", new LoginFormData());
        //retorna a página html do login 
        //ou seja leva para a página login
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        this.AuthenticationInformation.setIsAdmin(false);
        this.AuthenticationInformation.setIsAuthenticated(false);
        return "redirect:/home";
    }

    //se o link for /authenticaion
    @PostMapping("/authentication")
    //recebe o LoginFormData vindo do login.html
    //RedirectedAttributes--> passar atributos para o link redirecionado
    public String authentication(@ModelAttribute LoginFormData loginForm, RedirectAttributes m) {
        //este LoginFormData entra no método do serviço do User
        //no método vai ver se o user existe, se a passe está bem, se é user
        //(ver o método)
        int result = userService.authenticate(loginForm);
        //se o user existe e a passe estiver bem
        if (result != 0) {
            //passo a por que nesta sessão o user está autenticado
            this.AuthenticationInformation.setIsAuthenticated(true);
            //se for admin
            if (result == 1) {
                //passo a por que nesta sessão o user é um admin
                this.AuthenticationInformation.setIsAdmin(true);
            }
        }
        else{
            //Se não conseguiu autenticar-se
            m.addAttribute("error", 0);
        }
        //redireciona para o "/home"
        return "redirect:/home";
    }

    //se o link for /create-user
    @GetMapping("/create-user")
    public String createUserForm(Model model) {
        if (this.AuthenticationInformation.getIsAdmin()){
        //dentro do user_ vou por um objeto User_ criado vazio
        //para depois ser passado para o html
        model.addAttribute("user_", new User_());
        //retorna a página html do create-user 
        //ou seja leva para a página create-user
        return "create-user";
        }else{
            return "redirect:/home";
         }
    }

    //se o link for /create-player
    @GetMapping("/create-player")
    public String createPlayerForm(Model model) {
        if (this.AuthenticationInformation.getIsAdmin()){
        //dentro do player vou por um objeto Player criado vazio
        //para depois ser passado para o html
        model.addAttribute("player", new Player());
        //dentro do allTeams vou por as teams todas da BD
        //as teams vêm do teamService.getAllTeams()
        model.addAttribute("allTeams", teamService.getAllTeams());
        //retorna a página html do create-player
        //ou seja leva para a página create-player
        return "create-player";
         }else{
            return "redirect:/home";
         }
    }

    @PostMapping("/save-player")
    public String savePlayer_Submission(@ModelAttribute Player player, Model model) {
        FormResult result = new FormResult("player");
        if (playerService.addPlayer(player)) {
            result.setResult("Sucess");
        } else {
            result.setResult("Failure");
        }
        model.addAttribute("FormResult", result);
        return "result";
    }

    @GetMapping("/create-team")
    public String createTeamForm(Model model) {
        if (this.AuthenticationInformation.getIsAdmin()){
            model.addAttribute("team", new TeamForm());
            return "create-team";
        }else{
            return "redirect:/home";
        }
    }

    @PostMapping("/save-team")
    public String saveTeam_Submission(@ModelAttribute TeamForm teamForm, Model model) {
        FormResult result = new FormResult("team");
        if (teamService.addTeam(teamForm)) {
            result.setResult("Sucess");
        } else {
            result.setResult("Failure");
        }
        model.addAttribute("FormResult", result);
        return "result";
    }

    //se o link for /save-user
    @PostMapping("/save-user")
    //recebe o User_ vindo do create-user.html
    public String saveUser_Submission(@ModelAttribute User_ user_, Model model) {
        //cria um objeto do tipo FormResult com a string user nele
        FormResult result = new FormResult("user");
        //se for possível adicionar o user
        if (userService.addUser(user_)) {
            //o parâmetro result do objeto FormResult será "Sucess"
            result.setResult("Sucess");
        } 
        //se não
        else {
            //o parâmetro result do objeto FormResult será "Failure"
            result.setResult("Failure");
        }
        //dentro do FormResult vou por um objeto FormResult criado
        //para depois ser passado para o html
        model.addAttribute("FormResult", result);
        //retorna a página html do result 
        //ou seja leva para a página result
        return "result";
    }


  
    @GetMapping("/edit-game")
    public String editGame(Model model) {
        if (this.AuthenticationInformation.getIsAdmin()){
        model.addAttribute("allGames", gameService.getAllGames());
        model.addAttribute("game", new Game());
        
        return "edit-game";
    }else{
        return "redirect:/home";
     }
    }

    @GetMapping("/edit-player")
    public String editPlayer(Model m){
        m.addAttribute("players", this.playerService.getAllPlayers());
        m.addAttribute("allTeams", this.teamService.getAllTeams());
        m.addAttribute("p", new FormPlayer());
        return "edit-player";
    }

    @PostMapping("/save-edit")
    public String saveEditPlayer(@ModelAttribute FormPlayer form){
        if(this.playerService.findByName(form.getNewName()) == 0){
            this.playerService.changePlayer(form.getNewName(), form.getName(), form.getPosition(), form.getBirthDate(), form.getNewTeam());


        }
        return "redirect:/home";
    }
 
    @PostMapping("/save-edit-game")
    public String saveEditGame(@ModelAttribute Game game) {
       
        gameService.updateGame(game.getId(), game.getLocation(), game.getDate());
        return "redirect:/home";
    }


    @GetMapping("/create-game")
    public String createGame(Model model) {
        if (this.AuthenticationInformation.getIsAdmin()){
        model.addAttribute("game", new Game());
        model.addAttribute("allTeams", this.teamService.getAllTeams());
        return "create-game";
    }else{
        return "redirect:/home";
     }
    }

    @PostMapping("/save-game")
    public String saveGame_Submission(@ModelAttribute Game game, Model model) {
        gameService.addGame(game);
        FormResult result = new FormResult("game");
        if (gameService.addGame(game)) {
            result.setResult("Sucess");
        } else {
            result.setResult("Failure");
        }
        model.addAttribute("FormResult", result);
        return "result";
    }

    @GetMapping("/create-event")
    public String createEvent(@RequestParam(name = "id", required = true) int id, Model model) {
        if (this.AuthenticationInformation.getIsAuthenticated()){
        Optional<Game> op = gameService.findById(id);
        FormEvent fevent = new FormEvent();
            if (op.isPresent()) {
                model.addAttribute("game", op.get());
                model.addAttribute("fevent", fevent);
                model.addAttribute("typesevents", eventService.getTypeEvents());
                return "create-event";
            } else {
                return "redirect:/home";
            }
        }else{
            return "redirect:/home";
        }
    }

    @PostMapping("/save-event")
    public String saveEvent_Submission(@ModelAttribute FormEvent fevent, Model model) {
        FormResult result = new FormResult("event");
        //Se não escolhi o player A o player que vai estar no evento é o player B
        if(fevent.getPlayerA() == null){
            fevent.getEvent().setPlayer(fevent.getPlayerB());
        }
        if(fevent.getPlayerB() == null){
            fevent.getEvent().setPlayer(fevent.getPlayerA());
        }
        int res = eventService.addEvent(fevent.getEvent());
        if (res == 0) {
            result.setResult("Sucess");
        } else {
            model.addAttribute("Error", Integer.toString(res));
            result.setResult("Failure");
        }
        model.addAttribute("id", fevent.getEvent().getGame().getId());
        model.addAttribute("FormResult", result);
        return "result";
    }
    

     
}
