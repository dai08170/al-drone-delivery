package fr.unice.polytech.si5.al.projet.s3.truck.step;

import fr.unice.polytech.si5.al.projet.s3.truck.DroneDeliveryApp;

public abstract class ExecutableStep {
	enum TaskStatus {
		DONE,
		FAILED,
		PENDING,
		PROCESSING
	}

	protected String name;
	protected TaskStatus status;

	protected ExecutableStep(String name) {
		this.name = name;
		this.status = TaskStatus.PENDING;
	}

	public boolean isDone() {
		return this.status == TaskStatus.DONE;
	}

	public boolean isFailed() {
		return this.status == TaskStatus.FAILED;
	}

	public boolean isPending() {
		return this.status == TaskStatus.PENDING;
	}

	public boolean isProcessing() {
		return this.status == TaskStatus.PROCESSING;
	}

	public String getName() {
		return this.name;
	}

	abstract String getDescription();

	//	For data injection
	abstract void execute(DroneDeliveryApp app);
}