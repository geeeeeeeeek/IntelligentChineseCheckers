package graphics.listeners;

import controller.Services;
import controller.Trace;

import java.awt.event.ActionEvent;

/**
 * Class retractListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class retractListener extends ListenerFactory {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            Trace.getTrace().retract();
        } catch (Exception e) {
            Services.msg("No more retract!");
        }
    }

}
