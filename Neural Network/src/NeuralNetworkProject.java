

public class NeuralNetworkProject
{
    //
    //https://github.com/donnguyen/UIT-ANPR/blob/master/src/com/hazuu/uitanpr/neural/KohonenNetwork.java

    public static void main(String[] args)
    {
        View view;
        Controller controller;
        /////////////////////////////////////

        // Se crea el view.
        view = new View("Neural Network");

        // Se crea el controller,
        controller = new Controller(view);
        // y se asocia al view.
        view.setActionListener(controller);

        // Se inicia la ejecución de la aplicación.
        view.inicia();
    }//end main

}//end Main class

