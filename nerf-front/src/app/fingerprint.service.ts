import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class FingerprintService {

  constructor(private http: HttpClient) { }

  enroll(): Observable<any> {
    return this.http.get(environment.url + '/fingerprint/enroll');
  }

  check(): Observable<any> {
    return this.http.get(environment.url + '/fingerprint/check');
  }

  shoot(): Observable<any> {
    return this.http.get(environment.url + '/gun/shoot');
  }
}
