package be.ucll.config;

import be.ucll.dao.PlayerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayerUserDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    public PlayerUserDetailsService(PlayerRepository playerRepository){this.playerRepository = playerRepository;}

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{
        return new PlayerPrincipal(playerRepository.findPlayerByLeagueNameIgnoreCase(s)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("User %s not found", s))));
    }
}
