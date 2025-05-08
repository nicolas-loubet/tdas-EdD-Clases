package ar.edu.uns.cs.ed.tdas.tdamapeo;

public class TesteoEnClase {

	public static void main(String[] args) {
		Map<Character,Integer> mapeo= new MapConLista<Character,Integer>();
		int total= 0;
		
		String texto= "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin purus elit, sit amet sagittis elit ultricies at. Morbi lobortis lacus interdum, malesuada neque vitae, imperdiet nulla. Quisque aliquam dictum posuere. Praesent euismod ullamcorper rutrum. Nam id arcu sit amet odio commodo congue. Cras id risus lorem. Vivamus consectetur faucibus arcu, et consequat libero mollis et. Nam dapibus feugiat orci et tincidunt. Etiam eu eleifend ipsum. In blandit justo a lorem cursus eleifend. Nunc rhoncus et magna vel pretium. Ut facilisis auctor ex, a varius ipsum rhoncus eu. Sed venenatis, urna eget congue mollis, enim magna semper urna, vitae tempor lacus nibh vel tortor. Proin sed urna condimentum, auctor diam sit amet, iaculis nisl. Donec lobortis augue tortor.";
		for(char c: texto.toCharArray()) {
			if(c == ' ' || c == ',' || c == '.') continue;
			Integer cuenta= mapeo.get(c);
			cuenta= cuenta==null ? 0 : cuenta;;
			mapeo.put(c,cuenta+1);
			total++;
		}
		
		for(char c: mapeo.keys())
			System.out.println("Frec. de "+c+"= "+round(100.0*mapeo.get(c)/total)+"%");
	}

	private static String round(double value) {
		double scale= Math.pow(10,3);
		return String.format("%.3f",Math.ceil(value * scale) / scale);
	}

}
