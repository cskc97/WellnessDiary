package apps.everythingforward.com.wellnessdiary.database;

import fr.xebia.android.freezer.annotations.Id;
import fr.xebia.android.freezer.annotations.Model;

/**
 * Created by santh on 3/16/2017.
 */

@Model
public class GraphFilePaths {

    @Id long graphfileid;
    String graphImagePath;

    public GraphFilePaths()
    {

    }

    public GraphFilePaths(String graphImagePath) {
        this.graphImagePath = graphImagePath;
    }

    public String getGraphImagePath() {
        return graphImagePath;
    }

    public void setGraphImagePath(String graphImagePath) {
        this.graphImagePath = graphImagePath;
    }
}
