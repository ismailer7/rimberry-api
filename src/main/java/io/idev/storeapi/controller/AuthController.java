package io.idev.storeapi.controller;

import io.idev.storeapi.StoreApiConstants;
import io.idev.storeapi.api.controller.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(StoreApiConstants.API_BASE_URI)
@RestController
@RequiredArgsConstructor
public class AuthController implements UserApi {



}
