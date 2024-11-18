import { defineFontProvider } from 'unifont'

const resolvableFonts = new Set<string>()
export default defineFontProvider('custom', () => {
  // Do some stuff
  resolvableFonts.add('SomeFontFromCustomProvider')
  return {
    async resolveFont(fontFamily: string) {
      if (!resolvableFonts.has(fontFamily)) {
        return
      }
      return {
        fonts: [
          { src: [{ url: '/fonts/custom-font.woff2', format: 'woff2' }] },
        ],
      }
    },
  }
})
