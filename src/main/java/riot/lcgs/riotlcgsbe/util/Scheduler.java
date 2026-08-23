package riot.lcgs.riotlcgsbe.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import riot.lcgs.riotlcgsbe.service.MainService;

import static riot.lcgs.riotlcgsbe.util.DateTimeTool.dateTimeCurrent;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {
    private final MainService mainService;

    @Scheduled(cron = "0 0 9 * * ?") // 매일 아침 9시
    public void imageUploadSchedule() {
        String now = dateTimeCurrent().getData();
        log.info("ImageUpload Scheduler 작업 실행 - " + now);
        mainService.LCGCustomGameImageSave(false);
    }

    @Scheduled(cron = "0 0 10 * * ?") // 매일 아침 10시
    public void patchNoteUpdateSchedule() {
        String now = dateTimeCurrent().getData();
        log.info("PatchNoteUpdate Scheduler 작업 실행 - " + now);
        mainService.LCGPatchNoteSave(false, "");
    }
}
