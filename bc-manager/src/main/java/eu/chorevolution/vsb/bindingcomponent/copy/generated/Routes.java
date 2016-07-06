
package eu.chorevolution.vsb.bindingcomponent.copy.generated;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * This class was generated by the CHOReVOLUTION BindingComponent Generator using com.sun.codemodel 2.6
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Routes")
public class Routes {

    @XmlElement(name = "bounds", required = true)
    private Bounds bounds;
    @XmlElement(name = "copyrights", required = true)
    private String copyrights;
    @XmlElement(name = "legs", required = true)
    private List<Legs> legs;
    @XmlElement(name = "overview_polyline", required = true)
    private OverviewPolyline overview_polyline;
    @XmlElement(name = "summary", required = true)
    private String summary;
    @XmlElement(name = "warnings", required = true)
    private List<Warnings> warnings;
    @XmlElement(name = "waypoint_order", required = true)
    private List<WaypointOrder> waypoint_order;

    public Bounds getbounds() {
        return bounds;
    }

    public void setbounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public String getcopyrights() {
        return copyrights;
    }

    public void setcopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public List<Legs> getlegs() {
        return legs;
    }

    public void setlegs(List<Legs> legs) {
        this.legs = legs;
    }

    public OverviewPolyline getoverview_polyline() {
        return overview_polyline;
    }

    public void setoverview_polyline(OverviewPolyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public String getsummary() {
        return summary;
    }

    public void setsummary(String summary) {
        this.summary = summary;
    }

    public List<Warnings> getwarnings() {
        return warnings;
    }

    public void setwarnings(List<Warnings> warnings) {
        this.warnings = warnings;
    }

    public List<WaypointOrder> getwaypoint_order() {
        return waypoint_order;
    }

    public void setwaypoint_order(List<WaypointOrder> waypoint_order) {
        this.waypoint_order = waypoint_order;
    }

}
