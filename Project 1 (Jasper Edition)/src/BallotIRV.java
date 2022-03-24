

public class BallotIRV {

    /** 
     * Creates a BallotIRV object
     * @param rank_arr populates the ballot with the orders in which the candidates are ranked
     */
    public BallotIRV(int rank_arr) {

        private Queue<integer> ranking; // Need to autopopulate the ranking queue with the parameter.
                                        // Becomes trickier with ballots that don't have all four candidates ranked.
                                        // Can either do most computation in here, or do most in ElectionIRV and 
                                        // do a simple queue add based on the array passed in. Shana mentioned in class
                                        // how for IRV, it might be helpful to assign numbers to the candidates. 

        CandidateIRV currCandidate; // initialize the first choice candidate and update the next preferrential candidate as 
                                    // candidates get eliminated. Use nextCandidate() in order to update.
    }

    public nextCandidate() {
        // todo
        // Not sure what this means, but it's in the class diagram
    }

}
