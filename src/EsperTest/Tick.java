
package EsperTest;

/**
 *Event object, populate in GenerateStream
 * @author gs023850
 */
 public class Tick {

     
     
    String attribute1;
    String attribute2;
    String attribute3;
    String attribute4;
    String attribute5;
    String attribute6;
    String attribute7;
    String attribute8;

    public Tick(){};
    
    public void PopulateTick(String attribute1,String attribute2,String attribute3,
                            String attribute4, String attribute5, String attribute6, 
                           String attribute7, String attribute8){

    this.attribute1 = attribute1;
    this.attribute2 = attribute2;
    this.attribute3 = attribute3;
    this.attribute4 = attribute4;
    this.attribute5 = attribute5;
    this.attribute6 = attribute6;
    this.attribute7 = attribute7;
    this.attribute8 = attribute8;
    }

    public String getAttribute1() {return attribute1;}
    public String getAttribute2() {return attribute2;}
    public String getAttribute3() {return attribute3;}
    public String getAttribute4() {return attribute4;}
    public String getAttribute5() {return attribute5;}
    public String getAttribute6() {return attribute6;}
    public String getAttribute7() {return attribute7;}
    public String getAttribute8() {return attribute8;}





    
}
