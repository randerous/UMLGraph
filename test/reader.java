import java.net.


public class reader{
	public static void main(String[] args) {
		URI fichier = URI.createURI("essais.uml");
		load(fichier);
		}

		protected static void load(org.eclipse.emf.common.util.URI uri) {
		//Package package_ = null;
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(uri);
		System.out.println(resourceSet.getResources().size());
		for (
		TreeIterator<EObject> i = resource.getAllContents();
		i .hasNext();
		) {
		EObject current = i.next();
		if (!(current instanceof NamedElement))
		i.prune();
		NamedElement asNamed = (NamedElement) current;
		System.out.println(asNamed.getQualifiedName()
		+ " : " + asNamed.eClass().getName());
		}

		}
	
}