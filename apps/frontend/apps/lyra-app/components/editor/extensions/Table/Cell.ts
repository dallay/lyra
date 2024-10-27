import TableCell from "@tiptap/extension-table-cell";
import { Plugin } from "@tiptap/pm/state";
import { Decoration, DecorationSet } from "@tiptap/pm/view";
import { getCellsInColumn, isRowSelected, selectRow } from "./utils";

export const CustomTableCell = TableCell.extend({
  addAttributes() {
    return {
      ...(this.parent?.() || []),
      backgroundColor: {
        default: null,
        parseHTML: (element) => element.getAttribute("data-background-color"),
        renderHTML: (attributes) => {
          return {
            "data-background-color": attributes.backgroundColor,
            style: `background-color: ${attributes.backgroundColor}`,
          };
        },
      },
    };
  },

  addProseMirrorPlugins() {
    const { isEditable } = this.editor;

    return [
      new Plugin({
        props: {
          decorations: (state) => {
            if (!isEditable) {
              return DecorationSet.empty;
            }

            const { doc, selection } = state
            const decorations: Decoration[] = []
            const cells = getCellsInColumn(0)(selection)

            if (cells) {
              cells.forEach(({ pos }: { pos: number }, index: number) => {
                decorations.push(
                  Decoration.widget(pos + 1, () => {
                    const rowSelected = isRowSelected(index)(selection)
                    let className = 'grip-row'

                    if (rowSelected) {
                      className += ' selected'
                    }

                    if (index === 0) {
                      className += ' first'
                    }

                    if (index === cells.length - 1) {
                      className += ' last'
                    }

                    const grip = document.createElement('a')

                    grip.className = className
                    grip.addEventListener('mousedown', event => {
                      event.preventDefault()
                      event.stopImmediatePropagation()

                      this.editor.view.dispatch(selectRow(index)(this.editor.state.tr))
                    })

                    return grip

                  }),
                )
              })
            }

            return DecorationSet.create(doc, decorations);
          },
        },
      }),
    ];
  },
});
