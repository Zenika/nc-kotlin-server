import { Injectable } from '@angular/core'
import { Http } from '@angular/http'
import { Observable } from 'rxjs/Observable'
import 'rxjs/add/observable/of'
import 'rxjs/add/operator/map'
import { apiUrl } from './api.conf'

export interface Language {
  key: string
  avatarImg: string
}

@Injectable()
export class LanguageService {

  constructor(private http: Http) {
  }

  public get languages(): Observable<Language[]> {
    return this.http.get(`${apiUrl}/language`)
      .map(res => res.json())
  }
}
