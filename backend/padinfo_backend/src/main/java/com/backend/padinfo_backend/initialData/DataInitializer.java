package com.backend.padinfo_backend.initialData;

import com.backend.padinfo_backend.model.entity.Player;
import com.backend.padinfo_backend.model.entity.Role;
import com.backend.padinfo_backend.model.entity.Tournament;
import com.backend.padinfo_backend.model.entity.UserInfo;
import com.backend.padinfo_backend.model.enums.ERole;
import com.backend.padinfo_backend.model.repository.IPlayerRepository;
import com.backend.padinfo_backend.model.repository.IRoleRepository;
import com.backend.padinfo_backend.model.repository.ITournamentRepository;
import com.backend.padinfo_backend.model.repository.IUserInfoRepository;
import com.backend.padinfo_backend.model.service.Authentication.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    @Autowired
    private IUserInfoRepository userInfoRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private ITournamentRepository tournamentRepository;

    @Autowired
    private IPlayerRepository playerRepository;

    @Autowired
    private IAuthenticationService authenticationService;

    private Role rAdmin;
    private Role rUser;
    private Role rGuest;


    public DataInitializer(ITournamentRepository tournamentRepository, IPlayerRepository playerRepository, IRoleRepository roleRepository, IUserInfoRepository userInfoRepository, IAuthenticationService authenticationService) {
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
        this.roleRepository = roleRepository;
        this.userInfoRepository = userInfoRepository;
        this.authenticationService = authenticationService;
        initData();
    }

    private void initData() {

        if (tournamentRepository.count() == 0) {
            saveTournamentData();
        }

        if (playerRepository.count() == 0) {
            savePlayerData();
        }

        if (roleRepository.count() == 0) {
            saveRoleData();
        }

        if (userInfoRepository.count() == 0) {
            saveUserData();
        }

    }

    private void saveTournamentData() {
        Tournament t1 = Tournament.builder()
                .city("Abu Dhabi")
                .imageURL("wpt_abudhabi")
                .name("Modon Abu Dhabi Master 2023")
                .build();

        Tournament t2 = Tournament.builder()
                .city("La Rioja")
                .imageURL("wpt_larioja_open")
                .name("La Rioja Open 1000")
                .build();

        Tournament t3 = Tournament.builder()
                .city("Santiago de Chile")
                .imageURL("wpt_chile_open")
                .name("BTG Pactual Chile Padel Open 1000")
                .build();

        Tournament t4 = Tournament.builder()
                .city("Asunción")
                .imageURL("wpt_paraguay_open")
                .name("Paraguay Padel Open 1000")
                .build();

        Tournament t5 = Tournament.builder()
                .city("Reus")
                .imageURL("wpt_reus_open")
                .name("Tau Cerámica Reus Costa Daurada Open 500")
                .build();

        Tournament t6 = Tournament.builder()
                .city("Granada")
                .imageURL("wpt_granada_open")
                .name("Cervezas Victoria Granada Open")
                .build();

        Tournament t7 = Tournament.builder()
                .city("Bruselas")
                .imageURL("wpt_bruselas_open")
                .name("Circus Brussels Padel Open 2023")
                .build();

        Tournament t8 = Tournament.builder()
                .city("Alicante")
                .imageURL("wpt_alicante_open")
                .name("Tau Cerámica Alicante Open 500")
                .build();

        Tournament t9 = Tournament.builder()
                .city("Vigo")
                .imageURL("wpt_vigo_open")
                .name("Estrella Damm Vigo Open 2023")
                .build();

        Tournament t10 = Tournament.builder()
                .city("Hillerød")
                .imageURL("wpt_hillerod_open")
                .name("Cupra Danish Padel Open 2023")
                .build();

        Tournament t11 = Tournament.builder()
                .city("Vienna")
                .imageURL("wpt_vienna_open")
                .name("Boss Vienna Padel Open 2023")
                .build();

        Tournament t12 = Tournament.builder()
                .city("Marbella")
                .imageURL("wpt_marbella_master")
                .name("Cervezas Victoria Marbella Master 2023")
                .build();

        Tournament t13 = Tournament.builder()
                .city("Toulouse")
                .imageURL("wpt_toulouse_open")
                .name("Human French Padel Open 2023")
                .build();

        Tournament t14 = Tournament.builder()
                .city("Valladolid")
                .imageURL("wpt_valladolid_master")
                .name("Barceló Valladolid Master 2023")
                .build();

        Tournament t15 = Tournament.builder()
                .city("Valencia")
                .imageURL("wpt_valencia_open")
                .name("Adeslas València Open 2023")
                .build();

        Tournament t16 = Tournament.builder()
                .city("Málaga")
                .imageURL("wpt_malaga_open")
                .name("Cervezas Victoria Málaga Open 2023")
                .build();

        Tournament t17 = Tournament.builder()
                .city("Nokia")
                .imageURL("wpt_nokia_open")
                .name("Aare Invest Finland Padel Open 2023")
                .build();

        Tournament t18 = Tournament.builder()
                .city("Madrid")
                .imageURL("wpt_madrid_masters")
                .name("Sixt Comunidad de Madrid Master 2023")
                .build();

        Tournament t19 = Tournament.builder()
                .city("Düsseldorf")
                .imageURL("wpt_dusseldorf_open")
                .name("Boss German Padel Open 2023")
                .build();

        Tournament t20 = Tournament.builder()
                .city("Amsterdam")
                .imageURL("wpt_amsterdam_open")
                .name("Decathlon Amsterdam Open 2023")
                .build();

        Tournament t21 = Tournament.builder()
                .city("Menorca")
                .imageURL("wpt_menorca_open")
                .name("Estrella Damm Menorca Open 2023")
                .build();

        Tournament t22 = Tournament.builder()
                .city("Malmö")
                .imageURL("wpt_malmo_open")
                .name("Areco Malmö Padel Open 2023")
                .build();

        Tournament t23 = Tournament.builder()
                .city("Ciudad de México")
                .imageURL("wpt_ciudaddemexico_open")
                .name("Skechers Mexico Padel Open 2023")
                .build();

        Tournament t24 = Tournament.builder()
                .city("Barcelona")
                .imageURL("wpt_barcelona_masters_final")
                .name("Boss Barcelona Master Final 2023")
                .build();

        tournamentRepository.saveAll(List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24));
    }

    private void savePlayerData() {
        Player p1 = Player.builder()
                .gender("masc")
                .imageURL("arturo_coello")
                .name("Arturo Coello")
                .points("17.745 puntos")
                .rankingPosition(1)
                .build();

        Player p2 = Player.builder()
                .gender("masc")
                .imageURL("agustin_tapia")
                .name("Agustín Tapia")
                .points("17.745 puntos")
                .rankingPosition(1)
                .build();

        Player p3 = Player.builder()
                .gender("masc")
                .imageURL("franco_stupaczuk")
                .name("Franco Stupaczuk")
                .points("15.410 puntos")
                .rankingPosition(3)
                .build();

        Player p4 = Player.builder()
                .gender("masc")
                .imageURL("martin_di_nenno")
                .name("Martin Di Nenno")
                .points("15.410 puntos")
                .rankingPosition(3)
                .build();

        Player p5 = Player.builder()
                .gender("masc")
                .imageURL("ale_galan")
                .name("Ale Galán")
                .points("10.510 puntos")
                .rankingPosition(5)
                .build();

        Player p6 = Player.builder()
                .gender("fem")
                .imageURL("paula_josemaria")
                .name("Paula Josemaría")
                .points("19.220 puntos")
                .rankingPosition(1)
                .build();

        Player p7 = Player.builder()
                .gender("fem")
                .imageURL("ariana_sanchez")
                .name("Ariana Sánchez")
                .points("19.220 puntos")
                .rankingPosition(1)
                .build();

        Player p8 = Player.builder()
                .gender("fem")
                .imageURL("gemma_tiray")
                .name("Gemma Triay")
                .points("14.890 puntos")
                .rankingPosition(3)
                .build();

        Player p9 = Player.builder()
                .gender("fem")
                .imageURL("bea_gonzalez")
                .name("Bea González")
                .points("13.680 puntos")
                .rankingPosition(4)
                .build();

        Player p10 = Player.builder()
                .gender("fem")
                .imageURL("delfi_brea")
                .name("Delfi Brea")
                .points("13.360 puntos")
                .rankingPosition(5)
                .build();

        playerRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    private void saveRoleData() {
        rAdmin = Role.builder()
                .id(Long.valueOf(1))
                .name("ADMIN")
                .build();

        rUser = Role.builder()
                .id(Long.valueOf(2))
                .name("USER")
                .build();

        rGuest = Role.builder()
                .id(Long.valueOf(3))
                .name("GUEST")
                .build();

        roleRepository.saveAll(List.of(rAdmin, rUser, rGuest));
    }

    private void saveUserData() {
        UserInfo u1 = UserInfo.builder()
                .username("dani")
                .password("1234")
                .name("Daniel")
                .lastname("Garcia")
                .email("dan1@gmail.com")
                .imageURL("admin_img")
                .roles(List.of(rAdmin, rUser, rGuest))
                .build();

        authenticationService.signup(u1);

        UserInfo u2 = UserInfo.builder()
                .username("pepe")
                .password("1234")
                .name("Pepe")
                .lastname("López")
                .email("pepe@gmail.com")
                .imageURL("admin_img")
                .roles(List.of(rAdmin, rUser, rGuest))
                .build();

        authenticationService.signup(u2);

        UserInfo u3 = UserInfo.builder()
                .username("pablo")
                .password("1234")
                .name("Pablo")
                .lastname("Coves")
                .email("pablo@gmail.com")
                .imageURL("admin_img")
                .roles(List.of(rAdmin, rUser, rGuest))
                .build();

        authenticationService.signup(u3);

        UserInfo u4 = UserInfo.builder()
                .username("maria")
                .password("1234")
                .name("María")
                .lastname("Rodríguez")
                .email("maria@gmail.com")
                .imageURL("admin_img")
                .roles(List.of(rAdmin, rUser, rGuest))
                .build();

        authenticationService.signup(u4);
    }
}