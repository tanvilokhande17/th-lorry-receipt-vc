package com.amazonaws.lambda.createlorryreceipt.models.responses;

public class AffinidiSignedVCProof {

	private String type;

	private String created;

	private String verificationMethod;

	private String proofPurpose;

	private String jws;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getVerificationMethod() {
		return verificationMethod;
	}

	public void setVerificationMethod(String verificationMethod) {
		this.verificationMethod = verificationMethod;
	}

	public String getProofPurpose() {
		return proofPurpose;
	}

	public void setProofPurpose(String proofPurpose) {
		this.proofPurpose = proofPurpose;
	}

	public String getJws() {
		return jws;
	}

	public void setJws(String jws) {
		this.jws = jws;
	}
}
