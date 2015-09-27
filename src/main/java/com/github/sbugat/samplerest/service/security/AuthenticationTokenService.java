package com.github.sbugat.samplerest.service.security;

import java.security.SecureRandom;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.github.sbugat.samplerest.dao.UserDao;
import com.github.sbugat.samplerest.dao.UserTokenDao;
import com.github.sbugat.samplerest.model.User;
import com.github.sbugat.samplerest.model.UserToken;
import com.github.sbugat.samplerest.resource.UserResource;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Named
public class AuthenticationTokenService {

	/** SLF4J Xlogger. */
	private static final XLogger logger = XLoggerFactory.getXLogger(UserResource.class);

	@Inject
	private UserDao userDao;

	@Inject
	private UserTokenDao userTokenDao;

	private final byte[] secret = new byte[32];

	@PostConstruct
	public void initAuthentication() {
		// Generate random 256-bit (32-byte) secret
		final SecureRandom random = new SecureRandom();
		random.nextBytes(secret);

		logger.info("Generated 32 bytes secret token (Base64 encoded): {}", Base64.encode(secret).toString());
	}

	public String generateAuthenticationToken(final String username) {

		final User user = userDao.findByUsername(username);
		if (null == user) {

			return null;
		}

		final String authenticationToken;
		try {
			authenticationToken = generateJWT(username);

		} catch (final JOSEException exception) {

			logger.error("Error during JWT token generation", exception);
			return null;
		}

		final UserToken oldToken = userTokenDao.findByUser(user);

		final UserToken userToken = new UserToken();
		if (null != oldToken) {
			userToken.setId(oldToken.getId());
		}
		userToken.setUser(user);
		userToken.setTokenStatus(1);
		userToken.setAuthenticationToken(authenticationToken);
		userTokenDao.save(userToken);

		return authenticationToken;
	}

	private String generateJWT(final String username) throws JOSEException {
		// Create HMAC signer
		final JWSSigner signer = new MACSigner(secret);

		// Prepare JWT with claims set
		final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(username).expirationTime(new Date(new Date().getTime() + 60 * 1000)).claim("http://localhost:8080/", true).build();

		final SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

		// Apply the HMAC protection
		signedJWT.sign(signer);

		// Serialize to compact form, produces something like
		// eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NDMzODA1NDAsInN1YiI6ImNpZGlhbiIsImh0dHA6XC9cL2xvY2FsaG9zdDo4MDgwXC8iOnRydWV9.EkPxd0EfujgLrk35DX1XmvnmyJsFO8dqbnzsgg78coM
		return signedJWT.serialize();
	}
}
