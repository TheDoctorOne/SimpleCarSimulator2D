package Input;

public interface InputHandler extends Runnable {
	public void Handler();
	public void KeyRelease(int key);
	public void KeyPressed(int key);
}
