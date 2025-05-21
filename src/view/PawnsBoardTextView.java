package view;

import java.io.IOException;

/**
 * Represents a view capable of rendering a model to an appendable. This is used
 * by the controller to render the game.
 */
public interface PawnsBoardTextView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.)
   * to the given appendable.
   * @param out where to send the model information to
   * @throws IOException if the rendering fails for some reason
   */
  void render(Appendable out);
}