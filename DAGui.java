import javax.swing.JOptionPane;


public class DAGui {
    public String promptForSourceVertex() {
        String title = "Source Vertex";
        String prompt = "Which vertex do you want to start at?";
        String source = JOptionPane.showInputDialog(null, prompt, title,
                JOptionPane.QUESTION_MESSAGE);

        return source;
    }
    
    public String promptForDestinationVertex() {
        String title = "Destination Vertex";
        String prompt = "Which vertex do you want your destination to be?";
        String destination = JOptionPane.showInputDialog(null, prompt, title,
                JOptionPane.QUESTION_MESSAGE);

        return destination;
    }
}
