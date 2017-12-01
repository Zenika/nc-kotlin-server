import { DevfestCodeHeroFrontPage } from './app.po'

describe('devfest-code-hero-front App', () => {
  let page: DevfestCodeHeroFrontPage

  beforeEach(() => {
    page = new DevfestCodeHeroFrontPage()
  })

  it('should display welcome message', () => {
    page.navigateTo()
    expect(page.getParagraphText()).toEqual('Welcome to app!')
  })
})
