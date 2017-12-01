import { BrowserModule } from '@angular/platform-browser'
import { NgModule } from '@angular/core'
import { FlexLayoutModule } from '@angular/flex-layout'
import { HttpModule } from '@angular/http'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { HttpClientModule } from '@angular/common/http'
import { RouterModule, Routes } from '@angular/router'
import { MatDialogModule } from '@angular/material'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'

// technicals dependencies
import { AceEditorComponent } from 'ng2-ace-editor'

// business dependencies
import { AppComponent } from './app.component'
import { GameComponent } from './game/game.component'
import { LandingComponent } from './landing/landing.component'
import { EndComponent } from './end/end.component'
import { StepModalComponent } from './stepmodal/stepmodal.component'
import { ErrorModalComponent } from './errormodal/errormodal.component'
import { TestsModalComponent } from './testsmodal/testsmodal.component'
import { EditorComponent } from './game/editor/editor.component'
import { ConsoleComponent } from './game/console/console.component'
import { TestsComponent } from './game/tests/tests.component'
import { SceneComponent } from './game/scene/scene.component'
import { ScenarioComponent } from './game/scenario/scenario.component'

import { LanguageService } from './services/language.service'
import { PlayerService } from './services/player.service'
import { StateService } from './services/state.service'
import { CodeService } from './services/code.service'

const appRoutes: Routes = [
  { path: 'game', component: GameComponent },
  { path: 'landing', component: LandingComponent },
  { path: 'end', component: EndComponent },
  { path: '', redirectTo: '/landing', pathMatch: 'full' },
]

@NgModule({
  entryComponents: [
    TestsModalComponent,
    StepModalComponent,
    ErrorModalComponent,
  ],
  declarations: [
    GameComponent,
    LandingComponent,
    EndComponent,
    AppComponent,
    AceEditorComponent,
    StepModalComponent,
    ErrorModalComponent,
    TestsModalComponent,
    EditorComponent,
    SceneComponent,
    ScenarioComponent,
    ConsoleComponent,
    TestsComponent,
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ),
    MatDialogModule,
    NoopAnimationsModule,
  ],
  bootstrap: [AppComponent],
  providers: [
    LanguageService,
    PlayerService,
    StateService,
    CodeService,
  ],
})
export class AppModule { }
