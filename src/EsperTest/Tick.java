
package EsperTest;

/**
 *Event object, populate in GenerateStream
 * @author gs023850
 */
 public class Tick {

     
     
     
    double attribute1;
    double attribute2;
    double attribute3;
    String attribute4;

    public Tick(){};
    
    public void PopulateTick(double attribute1, double attribute2, 
        double attribute3,String attribute4){

    this.attribute1 = attribute1;
    this.attribute2 = attribute2;
    this.attribute3 = attribute3;
    this.attribute4 = attribute4;

    }
    public double getAttribute1() {return attribute1;}
    public double getAttribute2() {return attribute2;}
    public double getAttribute3() {return attribute3;}
    public String getAttribute4() {return attribute4;}




    
}
