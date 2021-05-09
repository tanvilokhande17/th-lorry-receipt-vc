package com.amazonaws.lambda.createlorryreceipt.models.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AffinidiSignedVC {

	@JsonProperty("@context")
	private List<Object> context;

	private String id;

	private List<String> type;

	private AffinidiUnsignedVCHolder holder;

	private AffinidiUnsignedCredentialDetail credentialSubject;

	private String issuanceDate;

	private String issuer;

	private AffinidiSignedVCProof proof;

	public List<Object> getContext() {
		return context;
	}

	public void setContext(List<Object> context) {
		this.context = context;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public AffinidiUnsignedVCHolder getHolder() {
		return holder;
	}

	public void setHolder(AffinidiUnsignedVCHolder holder) {
		this.holder = holder;
	}

	public AffinidiUnsignedCredentialDetail getCredentialSubject() {
		return credentialSubject;
	}

	public void setCredentialSubject(AffinidiUnsignedCredentialDetail credentialSubject) {
		this.credentialSubject = credentialSubject;
	}

	public String getIssuanceDate() {
		return issuanceDate;
	}

	public void setIssuanceDate(String issuanceDate) {
		this.issuanceDate = issuanceDate;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public AffinidiSignedVCProof getProof() {
		return proof;
	}

	public void setProof(AffinidiSignedVCProof proof) {
		this.proof = proof;
	}
}
