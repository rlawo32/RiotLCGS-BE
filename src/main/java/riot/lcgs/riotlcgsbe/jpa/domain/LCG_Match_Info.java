package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Match_Info")
public class LCG_Match_Info {

    @Id
    @NotNull
    @Column(name = "lcg_game_id")
    private Long lcgGameId;

    @NotNull
    @Column(name = "lcg_game_date")
    private String lcgGameDate;

    @NotNull
    @Column(name = "lcg_game_mode")
    private String lcgGameMode;

    @NotNull
    @Column(name = "lcg_game_type")
    private String lcgGameType;

    @NotNull
    @Column(name = "lcg_game_duration")
    private int lcgGameDuration;

    @NotNull
    @Column(name = "lcg_game_map")
    private int lcgGameMap;

    @NotNull
    @Column(name = "lcg_ver_main")
    private String lcgVerMain;

    @NotNull
    @Column(name = "lcg_max_gold")
    private int lcgMaxGold;

    @NotNull
    @Column(name = "lcg_max_crowd")
    private int lcgMaxCrowd;

    @NotNull
    @Column(name = "lcg_max_dpm")
    private int lcgMaxDpm;

    @NotNull
    @Column(name = "lcg_max_gpm")
    private int lcgMaxGpm;

    @NotNull
    @Column(name = "lcg_max_dpg")
    private int lcgMaxDpg;

    @NotNull
    @Column(name = "lcg_max_dpd")
    private int lcgMaxDpd;

    @NotNull
    @Column(name = "lcg_max_tpd")
    private int lcgMaxTpd;

    @NotNull
    @Column(name = "lcg_max_damage_total")
    private int lcgMaxDamageTotal;

    @NotNull
    @Column(name = "lcg_max_damage_taken")
    private int lcgMaxDamageTaken;

    @NotNull
    @Column(name = "lcg_ai_summary_content")
    private String lcgAiSummaryContent;

    @NotNull
    @Column(name = "lcg_ai_summary_verify")
    private String lcgAiSummaryVerify;

    @NotNull
    @Column(name = "lcg_game_capture_verify")
    private String lcgGameCaptureVerify;

    @Builder
    public LCG_Match_Info(Long lcgGameId, String lcgGameDate, String lcgGameMode,
                          String lcgGameType, int lcgGameDuration, int lcgGameMap,
                          String lcgVerMain, int lcgMaxGold, int lcgMaxCrowd, int lcgMaxDpm,
                          int lcgMaxGpm, int lcgMaxDpg, int lcgMaxDpd, int lcgMaxTpd,
                          int lcgMaxDamageTotal, int lcgMaxDamageTaken, String lcgAiSummaryContent,
                          String lcgAiSummaryVerify, String lcgGameCaptureVerify) {
        this.lcgGameId = lcgGameId;
        this.lcgGameDate = lcgGameDate;
        this.lcgGameMode = lcgGameMode;
        this.lcgGameType = lcgGameType;
        this.lcgGameDuration = lcgGameDuration;
        this.lcgGameMap = lcgGameMap;
        this.lcgVerMain = lcgVerMain;
        this.lcgMaxGold = lcgMaxGold;
        this.lcgMaxCrowd = lcgMaxCrowd;
        this.lcgMaxDpm = lcgMaxDpm;
        this.lcgMaxGpm = lcgMaxGpm;
        this.lcgMaxDpg = lcgMaxDpg;
        this.lcgMaxDpd = lcgMaxDpd;
        this.lcgMaxTpd = lcgMaxTpd;
        this.lcgMaxDamageTotal = lcgMaxDamageTotal;
        this.lcgMaxDamageTaken = lcgMaxDamageTaken;
        this.lcgAiSummaryContent = lcgAiSummaryContent;
        this.lcgAiSummaryVerify = lcgAiSummaryVerify;
        this.lcgGameCaptureVerify = lcgGameCaptureVerify;
    }
}
