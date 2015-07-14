package genericCheckpointing.util;

public class MyAllTypesSecond extends SerializableObject{
		private int myIntS;
		private String myStringS;
		private float myFloatS;
		private short myShortS;
		private char myCharS;
		
		

		public MyAllTypesSecond (int i, String st, float f, short sh, char c)
		{
			myIntS= i;
			myStringS = st;
			myFloatS = f;
			myShortS = sh;
			myCharS = c;
		}
		
		
		public int getMyInt ()
		{
		return myIntS;
		}

		public String getMyString ()
		{
		return myStringS;
		}
		public float getMyFloat ()
		{
		return myFloatS;
		}
		public short getMyShort ()
		{
		return myShortS;
		}
		public char getMyChar ()
		{
		return myCharS;
		}


		public void setMyInt (int i)
		{
		myIntS =i;
		}

		public void getMyString (String s)
		{
		 myStringS = s;
		}
		public void getMyFloat (float f)
		{
		myFloatS =f;
		}
		public void getMyShort (Short l)
		{
		myShortS = l;
		}
		public void getMyChar (char c)
		{
		myCharS = c;
		}
}
