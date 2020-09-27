
interface ISynthService {

	// first finger on x-y pad
	void primaryOn();
	void primaryOff();
	void primaryXY(float x, float y); // x and y must be between 0.0 and 1.0

	// second finger on x-y pad
	void secondaryOn();
	void secondaryOff();
	void secondaryXY(float x, float y);

	// synth config
	float[] getScale();
	boolean isDuet();
	int getOctaves();

}