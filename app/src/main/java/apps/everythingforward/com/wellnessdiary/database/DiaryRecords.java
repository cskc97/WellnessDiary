package apps.everythingforward.com.wellnessdiary.database;

import fr.xebia.android.freezer.annotations.Id;
import fr.xebia.android.freezer.annotations.Model;

/**
 * Created by santh on 3/14/2017.
 */

@Model
public class DiaryRecords {
    @Id long _id;
    String diaryEntryText;
    String diaryEntrySentiment;
    String diaryEntryDate;
    String diaryEntryTime;

    public DiaryRecords(String diaryEntryText, String diaryEntrySentiment, String diaryEntryDate, String diaryEntryTime) {

        this.diaryEntryText = diaryEntryText;
        this.diaryEntrySentiment = diaryEntrySentiment;
        this.diaryEntryDate = diaryEntryDate;
        this.diaryEntryTime = diaryEntryTime;
    }

    public DiaryRecords() {
    }

    public long get_id() {
        return _id;
    }

    public String getDiaryEntryText() {
        return diaryEntryText;
    }

    public void setDiaryEntryText(String diaryEntryText) {
        this.diaryEntryText = diaryEntryText;
    }

    public String getDiaryEntrySentiment() {
        return diaryEntrySentiment;
    }

    public void setDiaryEntrySentiment(String diaryEntrySentiment) {
        this.diaryEntrySentiment = diaryEntrySentiment;
    }

    public String getDiaryEntryDate() {
        return diaryEntryDate;
    }

    public void setDiaryEntryDate(String diaryEntryDate) {
        this.diaryEntryDate = diaryEntryDate;
    }

    public String getDiaryEntryTime() {
        return diaryEntryTime;
    }

    public void setDiaryEntryTime(String diaryEntryTime) {
        this.diaryEntryTime = diaryEntryTime;
    }
}
