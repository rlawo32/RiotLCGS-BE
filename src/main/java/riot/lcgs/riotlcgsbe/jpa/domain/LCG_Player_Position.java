package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import riot.lcgs.riotlcgsbe.web.dto.object.Player;
import riot.lcgs.riotlcgsbe.web.dto.object.RankData;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Player_Position")
public class LCG_Player_Position {

    @Id
    @Column(name = "lcg_summoner_puuid")
    private String lcgSummonerPuuid;

    @NotNull
    @Column(name = "lcg_summoner_nickname")
    private String lcgSummonerNickname;

    @NotNull
    @Column(name = "lcg_play_count")
    private Long lcgPlayCount;

    @NotNull
    @Column(name = "lcg_total_playtime")
    private Long lcgTotalPlaytime;

    @NotNull
    @Column(name = "lcg_position_top_count")
    private Long lcgPositionTopCount;

    @NotNull
    @Column(name = "lcg_position_jug_count")
    private Long lcgPositionJugCount;

    @NotNull
    @Column(name = "lcg_position_mid_count")
    private Long lcgPositionMidCount;

    @NotNull
    @Column(name = "lcg_position_adc_count")
    private Long lcgPositionAdcCount;

    @NotNull
    @Column(name = "lcg_position_sup_count")
    private Long lcgPositionSupCount;

    @NotNull
    @Column(name = "lcg_position_top_win")
    private Long lcgPositionTopWin;

    @NotNull
    @Column(name = "lcg_position_jug_win")
    private Long lcgPositionJugWin;

    @NotNull
    @Column(name = "lcg_position_mid_win")
    private Long lcgPositionMidWin;

    @NotNull
    @Column(name = "lcg_position_adc_win")
    private Long lcgPositionAdcWin;

    @NotNull
    @Column(name = "lcg_position_sup_win")
    private Long lcgPositionSupWin;

    @NotNull
    @Column(name = "lcg_position_top_playtime")
    private Long lcgPositionTopPlaytime;

    @NotNull
    @Column(name = "lcg_position_jug_playtime")
    private Long lcgPositionJugPlaytime;

    @NotNull
    @Column(name = "lcg_position_mid_playtime")
    private Long lcgPositionMidPlaytime;

    @NotNull
    @Column(name = "lcg_position_adc_playtime")
    private Long lcgPositionAdcPlaytime;

    @NotNull
    @Column(name = "lcg_position_sup_playtime")
    private Long lcgPositionSupPlaytime;

    public LCG_Player_Position playerPositionUpdate(String nickname, int duration, String line, boolean win) {
        this.lcgSummonerNickname = nickname;
        this.lcgPlayCount += 1L;
        this.lcgTotalPlaytime += (long) duration;
        this.lcgPositionTopCount += line.equals("TOP") ? 1L : 0L;
        this.lcgPositionJugCount += line.equals("JUG") ? 1L : 0L;
        this.lcgPositionMidCount += line.equals("MID") ? 1L : 0L;
        this.lcgPositionAdcCount += line.equals("ADC") ? 1L : 0L;
        this.lcgPositionSupCount += line.equals("SUP") ? 1L : 0L;
        this.lcgPositionTopWin += line.equals("TOP") && win ? 1L : 0L;
        this.lcgPositionJugWin += line.equals("JUG") && win ? 1L : 0L;
        this.lcgPositionMidWin += line.equals("MID") && win ? 1L : 0L;
        this.lcgPositionAdcWin += line.equals("ADC") && win ? 1L : 0L;
        this.lcgPositionSupWin += line.equals("SUP") && win ? 1L : 0L;
        this.lcgPositionTopPlaytime += line.equals("TOP") ? (long) duration : 0L;
        this.lcgPositionJugPlaytime += line.equals("JUG") ? (long) duration : 0L;
        this.lcgPositionMidPlaytime += line.equals("MID") ? (long) duration : 0L;
        this.lcgPositionAdcPlaytime += line.equals("ADC") ? (long) duration : 0L;
        this.lcgPositionSupPlaytime += line.equals("SUP") ? (long) duration : 0L;
        return this;
    }

    @Builder
    public LCG_Player_Position(String lcgSummonerPuuid, String lcgSummonerNickname, Long lcgPlayCount, Long lcgTotalPlaytime, 
                               Long lcgPositionTopCount, Long lcgPositionJugCount, Long lcgPositionMidCount, Long lcgPositionAdcCount, 
                               Long lcgPositionSupCount, Long lcgPositionTopWin, Long lcgPositionJugWin, Long lcgPositionMidWin, 
                               Long lcgPositionAdcWin, Long lcgPositionSupWin, Long lcgPositionTopPlaytime, Long lcgPositionJugPlaytime, 
                               Long lcgPositionMidPlaytime, Long lcgPositionAdcPlaytime, Long lcgPositionSupPlaytime) {
        this.lcgSummonerPuuid = lcgSummonerPuuid;
        this.lcgSummonerNickname = lcgSummonerNickname;
        this.lcgPlayCount = lcgPlayCount;
        this.lcgTotalPlaytime = lcgTotalPlaytime;
        this.lcgPositionTopCount = lcgPositionTopCount;
        this.lcgPositionJugCount = lcgPositionJugCount;
        this.lcgPositionMidCount = lcgPositionMidCount;
        this.lcgPositionAdcCount = lcgPositionAdcCount;
        this.lcgPositionSupCount = lcgPositionSupCount;
        this.lcgPositionTopWin = lcgPositionTopWin;
        this.lcgPositionJugWin = lcgPositionJugWin;
        this.lcgPositionMidWin = lcgPositionMidWin;
        this.lcgPositionAdcWin = lcgPositionAdcWin;
        this.lcgPositionSupWin = lcgPositionSupWin;
        this.lcgPositionTopPlaytime = lcgPositionTopPlaytime;
        this.lcgPositionJugPlaytime = lcgPositionJugPlaytime;
        this.lcgPositionMidPlaytime = lcgPositionMidPlaytime;
        this.lcgPositionAdcPlaytime = lcgPositionAdcPlaytime;
        this.lcgPositionSupPlaytime = lcgPositionSupPlaytime;
    }
}
