
// To be defined
// by the programmer
public class Problem
{

    public static Genome defineGenome()
    {
        Gene city;
        Genome theGenome;
        //------------

        theGenome = new Genome();

        city = new Gene(11, 14);
        theGenome.add(city);
        city = new Gene(14, 11);
        theGenome.add(city);
        city = new Gene(7, 13);
        theGenome.add(city);
        city = new Gene(12, 13);
        theGenome.add(city);
        city = new Gene(11, 6);
        theGenome.add(city);
        city = new Gene(13, 5);
        theGenome.add(city);
        city = new Gene(1, 19);
        theGenome.add(city);
        city = new Gene(15, 0);
        theGenome.add(city);
        city = new Gene(9, 20);
        theGenome.add(city);
        city = new Gene(14, 1);
        theGenome.add(city);
        city = new Gene(12, 15);
        theGenome.add(city);
        city = new Gene(19, 13);
        theGenome.add(city);
        city = new Gene(15, 6);
        theGenome.add(city);
        city = new Gene(2, 19);
        theGenome.add(city);
        city = new Gene(11, 1);
        theGenome.add(city);
        city = new Gene(1, 0);
        theGenome.add(city);
        city = new Gene(2, 16);
        theGenome.add(city);
        city = new Gene(4, 4);
        theGenome.add(city);
        city = new Gene(15, 2);
        theGenome.add(city);
        city = new Gene(2, 2);
        theGenome.add(city);

        Genome.setLength(theGenome.size());

        return theGenome;
    }//end defineGenome



    // Gets the distance between 2 cities
    public static double distance(Gene aCity, Gene otherCity)
    {
        int xDistance;
        int yDistance;
        double distance;
        //------------

        xDistance = Math.abs(aCity.getX() - otherCity.getX());
        yDistance = Math.abs(aCity.getY() - otherCity.getY());
        distance = Math.sqrt( (xDistance*xDistance) +
                              (yDistance*yDistance) );
        return distance;
    }//end distance



    // Gets the value of the fitness function for an individual
    public static double fitnessFunction(Individual anIndividual)
    {
        double travelDistance;
        int i;
        Gene departureCity;
        Gene destinationCity;
        //---------------

        if (anIndividual.fitnessValue == 0)
        {
            travelDistance = 0;
            // Loop through a sequence of cities (an Individual)
            i = 0;
            while(i < anIndividual.individualSize())
            {
                // Define the departure city.
                departureCity = anIndividual.getGene(i);
                // Define the destination city.
                if(i + 1 < anIndividual.individualSize())
                    destinationCity = anIndividual.getGene(i + 1);
                    //end if
                else
                    destinationCity = anIndividual.getGene(0);
                //end else

                // add the distance between the two cities
                travelDistance = travelDistance +
                        distance(departureCity,destinationCity);
                i = i + 1;
            }//end while
            anIndividual.fitnessValue = travelDistance;
        }//end if
        // we return the inverse of the travel distance
        // that corresponds to the individual
        return anIndividual.fitnessValue;

    }//end fitnessFunction

}//end class Problem
