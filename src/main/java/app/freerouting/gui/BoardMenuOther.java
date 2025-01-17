package app.freerouting.gui;

import app.freerouting.board.RoutingBoard;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.ResourceBundle;

public class BoardMenuOther extends JMenu {
  private final BoardFrame board_frame;
  private final ResourceBundle resources;

  /** Creates a new instance of BoardMenuOther */
  private BoardMenuOther(BoardFrame p_board_frame) {
    board_frame = p_board_frame;
    resources =
        ResourceBundle.getBundle(
            "app.freerouting.gui.BoardMenuOther", p_board_frame.get_locale());
  }

  /** Returns a new other menu for the board frame. */
  public static BoardMenuOther get_instance(BoardFrame p_board_frame) {
    final BoardMenuOther other_menu = new BoardMenuOther(p_board_frame);

    other_menu.setText(other_menu.resources.getString("other"));

    // Add Snapshots menu item
    JMenuItem snapshots = new JMenuItem();
    snapshots.setText(other_menu.resources.getString("snapshots"));
    snapshots.setToolTipText(other_menu.resources.getString("snapshots_tooltip"));
    snapshots.addActionListener(
        evt -> other_menu.board_frame.snapshot_window.setVisible(true));

    other_menu.add(snapshots);

    // Add Delete All Tracks and Vias menu item
    JMenuItem delete_all_tracks = new JMenuItem();
    delete_all_tracks.setText(other_menu.resources.getString("delete_all_tracks_and_vias"));
    delete_all_tracks.setToolTipText(other_menu.resources.getString("delete_all_tracks_and_vias_tooltip"));
    delete_all_tracks.addActionListener(
        evt -> {
          RoutingBoard board = other_menu.board_frame.board_panel.board_handling.get_routing_board();
          // delete all tracks and vias
          board.delete_all_tracks_and_vias();
          // update the board
          other_menu.board_frame.board_panel.board_handling.update_routing_board(board);
          // create a deep copy of the routing board
          board = other_menu.board_frame.board_panel.board_handling.deep_copy_routing_board();
          // update the board again
          other_menu.board_frame.board_panel.board_handling.update_routing_board(board);
          // create ratsnest
          other_menu.board_frame.board_panel.board_handling.create_ratsnest();
          // redraw the board
          other_menu.board_frame.board_panel.board_handling.repaint();
        });

    other_menu.add(delete_all_tracks);

    return other_menu;
  }
}
