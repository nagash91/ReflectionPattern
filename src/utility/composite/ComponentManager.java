package utility.composite;

/**
 * Created by nagash on 18/09/16.
 */


public class ComponentManager<CONTAINER extends IComponent<COMPOSITE>, COMPOSITE extends IComposite<COMPOSITE, CONTAINER>>
        extends Component<COMPOSITE>
{
    private CONTAINER container;

    protected ComponentManager() {} // for hibernate
    public ComponentManager(CONTAINER container) {
        this.container = container;
    }

    public CONTAINER getContainer(){
        return container;
    }


}
