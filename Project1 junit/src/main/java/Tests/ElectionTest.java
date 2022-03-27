package Tests;

public class ElectionTest
{
   
    public ElectionTest(){

    }
    /* I think these @'s need to be in each */
    @Test
    @DisplayName("hey")
    @RepeatedTest(3)
    public void runTests(){
    calcResults();
    getWinners();
    generateReport();
    }
}
