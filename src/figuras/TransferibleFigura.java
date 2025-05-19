package figuras;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TransferibleFigura implements Transferable {

    public static final DataFlavor FIGURA_FLAVOR = new DataFlavor(FiguraData.class, "FiguraData");

    private static final DataFlavor[] SUPPORTED_FLAVORS = { FIGURA_FLAVOR };

    private final FiguraData figura;

    /**
     * @param figura La instancia de FiguraData a transferir.
     */
    public TransferibleFigura(FiguraData figura) {
        this.figura = figura;
    }

    /**
     * @return Un array de DataFlavor soportados.
     */
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    /**
     * @param flavor El DataFlavor a verificar.
     * @return true si el flavor es soportado, false en caso contrario.
     */
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return FIGURA_FLAVOR.equals(flavor);
    }

    /**
     * @param flavor El DataFlavor solicitado.
     * @return Los datos en el formato solicitado (un objeto FiguraData en este
     *         caso).
     * @throws UnsupportedFlavorException Si el flavor no es soportado.
     * @throws IOException                Si ocurre un error de E/S al obtener los
     *                                    datos (poco probable aquí).
     */
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (FIGURA_FLAVOR.equals(flavor)) {
            return figura;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
