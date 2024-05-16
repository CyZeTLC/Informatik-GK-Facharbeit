package de.cyzetlc.roadsystem;

import de.cyzetlc.roadsystem.gui.Window;
import de.cyzetlc.roadsystem.handler.RoadHandler;
import de.cyzetlc.roadsystem.handler.VehicleHandler;
import de.cyzetlc.roadsystem.service.database.QueryHandler;
import de.cyzetlc.roadsystem.utils.json.JsonConfig;
import de.cyzetlc.roadsystem.utils.mysql.MySQLCredentials;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Getter
public class RoadSystem {
    @Getter
    private static RoadSystem instance;

    @Getter
    public static Logger logger = LoggerFactory.getLogger(RoadSystem.class.getName());

    private final RoadHandler roadHandler;
    private final VehicleHandler vehicleHandler;
    private final Window window;
    private final JsonConfig config;

    private final boolean useDatabase;
    private final int tickSpeed;

    private QueryHandler queryHandler;

    public RoadSystem(boolean useDatabase) {
        instance = this;
        this.useDatabase = useDatabase;
        this.config = new JsonConfig("./config.json", true);
        this.tickSpeed = this.config.getObject().getInt("tickSpeed");

        if (useDatabase) {
            this.queryHandler = new QueryHandler(new JsonConfig(this.config.getObject().getJSONObject("mysql")).load(MySQLCredentials.class));
            this.initializeMysql();
        }

        this.roadHandler = new RoadHandler();
        this.vehicleHandler = new VehicleHandler();
        this.vehicleHandler.generateVehicles(this.config.getObject().getInt("entitiesAmount"), this.config.getObject().getDouble("carsPercentage"));

        this.window = new Window();
    }

    /**
     * The `initializeMysql` function creates two tables, `intersections` and `branches`, if they do not already exist in
     * the database.
     */
    private void initializeMysql() {
        this.queryHandler.createBuilder("CREATE TABLE IF NOT EXISTS `intersections` (`name` VARCHAR(2) UNIQUE, `loc_x` INT, `loc_y` INT)").executeUpdateSync();
        this.queryHandler.createBuilder("CREATE TABLE IF NOT EXISTS `branches` (`numeric_id` INT AUTO_INCREMENT UNIQUE, `intersection_one` VARCHAR(3), `intersection_two` VARCHAR(3))").executeUpdateSync();
    }

    /**
     * The main method checks the command line arguments to determine whether to create a RoadSystem object with or without
     * a database connection.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            new RoadSystem(true);
        } else if (args.length == 1 && args[0].equals("--nodb")) {
                new RoadSystem(false);
        } else {
            getLogger().error("Use java -jar <file> (--usedb/--nodb)");
        }
    }
}
