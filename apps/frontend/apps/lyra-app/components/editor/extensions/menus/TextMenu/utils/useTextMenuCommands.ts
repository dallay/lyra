import { Editor } from '@tiptap/vue-3'

export function useTextMenuCommands(editor: Editor) {
  const onBold = () => editor.chain().focus().toggleBold().run()
  const onItalic = () => editor.chain().focus().toggleItalic().run()
  const onStrike = () => editor.chain().focus().toggleStrike().run()
  const onUnderline = () => editor.chain().focus().toggleUnderline().run()
  const onCode = () => editor.chain().focus().toggleCode().run()
  const onCodeBlock = () => editor.chain().focus().toggleCodeBlock().run()

  const onSubscript = () => editor.chain().focus().toggleSubscript().run()
  const onSuperscript = () => editor.chain().focus().toggleSuperscript().run()
  const onAlignLeft = () => editor.chain().focus().setTextAlign('left').run()
  const onAlignCenter = () => editor.chain().focus().setTextAlign('center').run()
  const onAlignRight = () => editor.chain().focus().setTextAlign('right').run()
  const onAlignJustify = () => editor.chain().focus().setTextAlign('justify').run()

  const onChangeColor = (color: HighlightOptions['color']) => editor.chain().setColor(color).run()
  const onClearColor = () => editor.chain().focus().unsetColor().run()

  interface HighlightOptions {
    color: string;
  }

  const onChangeHighlight = (color: HighlightOptions['color']) => editor.chain().setHighlight({ color }).run();
  const onClearHighlight = () => editor.chain().focus().unsetHighlight().run()

  const onLink = (url: string, inNewTab = false) => {
    editor
      .chain()
      .focus()
      .setLink({ href: url, target: inNewTab ? '_blank' : '' })
      .run()
  }

  interface FontOptions {
    font: string;
  }

  const onSetFont = (font: FontOptions['font']) => {
    if (!font || font.length === 0) {
      return editor.chain().focus().unsetFontFamily().run()
    }
    return editor.chain().focus().setFontFamily(font).run()
  }

  interface FontSizeOptions {
    fontSize: string;
  }

  const onSetFontSize = (fontSize: FontSizeOptions['fontSize']) => {
    if (!fontSize || fontSize.length === 0) {
      return editor.chain().focus().unsetFontSize().run()
    }
    return editor.chain().focus().setFontSize(fontSize).run()
  }

  const onParagraph = () =>
    editor
      .chain()
      .focus()
      .lift("taskItem")
      .liftListItem("listItem")
      .setParagraph()
      .run()

  const onBlockquote = () =>
    editor
      .chain()
      .focus()
      .lift("taskItem")
      .liftListItem("listItem")
      .setBlockquote()
      .run()

  return {
    onBold,
    onItalic,
    onStrike,
    onUnderline,
    onCode,
    onCodeBlock,
    onSubscript,
    onSuperscript,
    onAlignLeft,
    onAlignCenter,
    onAlignRight,
    onAlignJustify,
    onChangeColor,
    onClearColor,
    onChangeHighlight,
    onClearHighlight,
    onSetFont,
    onSetFontSize,
    onLink,
    onParagraph,
    onBlockquote,
  }
}
