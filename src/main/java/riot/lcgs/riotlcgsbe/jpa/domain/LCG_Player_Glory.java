package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Player_Glory")
public class LCG_Player_Glory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_num")
    private Long rowNum;

    @NotNull
    @Column(name = "lcg_summoner_puuid")
    private String lcgSummonerPuuid;

    @NotNull
    @Column(name = "lcg_summoner_nickname")
    private String lcgSummonerNickname;

    @NotNull
    @Column(name = "lcg_glory_id")
    private String lcgGloryId;

    @NotNull
    @Column(name = "lcg_glory_title")
    private String lcgGloryTitle;

    @NotNull
    @Column(name = "lcg_glory_info")
    private String lcgGloryInfo;

    @Column(name = "lcg_glory_grade")
    private Integer lcgGloryGrade;

    @NotNull
    @Column(name = "lcg_glory_unique")
    private String lcgGloryUnique;

    @NotNull
    @Column(name = "lcg_glory_active")
    private String lcgGloryActive;

    @NotNull
    @Column(name = "lcg_glory_hide")
    private String lcgGloryHide;

    @NotNull
    @Column(name = "lcg_update_date")
    private String lcgUpdateDate;

    public LCG_Player_Glory playerGloryActiveUpdate(String lcgUpdateDate) {
        this.lcgGloryActive = "N";
        this.lcgUpdateDate = lcgUpdateDate;
        return this;
    }

    public LCG_Player_Glory playerGloryInfoUpdate(String lcgGloryInfo, String lcgUpdateDate) {
        this.lcgGloryInfo = lcgGloryInfo;
        this.lcgUpdateDate = lcgUpdateDate;
        return this;
    }

    public LCG_Player_Glory playerGloryTitleUpdate(String lcgGloryTitle, String lcgUpdateDate) {
        this.lcgGloryTitle = lcgGloryTitle;
        this.lcgUpdateDate = lcgUpdateDate;
        return this;
    }

    @Builder
    public LCG_Player_Glory(String lcgSummonerPuuid, String lcgSummonerNickname, String lcgGloryId,
                            String lcgGloryTitle, String lcgGloryInfo, int lcgGloryGrade, String lcgGloryUnique,
                            String lcgGloryActive, String lcgGloryHide, String lcgUpdateDate) {
        this.lcgSummonerPuuid = lcgSummonerPuuid;
        this.lcgSummonerNickname = lcgSummonerNickname;
        this.lcgGloryId = lcgGloryId;
        this.lcgGloryTitle = lcgGloryTitle;
        this.lcgGloryInfo = lcgGloryInfo;
        this.lcgGloryGrade = lcgGloryGrade;
        this.lcgGloryUnique = lcgGloryUnique;
        this.lcgGloryActive = lcgGloryActive;
        this.lcgGloryHide = lcgGloryHide;
        this.lcgUpdateDate = lcgUpdateDate;
    }
}
