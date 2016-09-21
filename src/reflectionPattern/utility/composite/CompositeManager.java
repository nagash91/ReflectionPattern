package reflectionPattern.utility.composite;

import java.util.*;

/**
 * Created by nagash on 17/09/16.
 */


public class CompositeManager<CONTAINER extends IComposite<CONTAINER, COMPONENT>,  COMPONENT extends IComponent<CONTAINER>>
        extends Component

{

    private CONTAINER container;
    public CONTAINER getContainer(){
        return container;
    }


    public CompositeManager(CONTAINER container) {
        this.container = container;
    }

    private Set<COMPONENT> childs = new HashSet<>();


    public void addChild(COMPONENT child) {
        child.setParent(this.getContainer(), new CompositeManagerToken());
        this.childs.add(child);
    }

    public Set<COMPONENT> getChilds() {
        return Collections.unmodifiableSet(childs);
    }

    public void setChilds(Set<COMPONENT> childs) {
        this.childs = childs;
    }






    public static class CompositeManagerToken {
        /**
         *  Only the CompositeManager can create this token and call methods
         *  that require an instance of this token (Friend Methods with Java)
         */
        private CompositeManagerToken() {}
    }


}
