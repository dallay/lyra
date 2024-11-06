import BaseDragHandle from "@tiptap-pro/extension-drag-handle";
import tippy from "tippy.js";
import { twMerge } from "tailwind-merge";
import { useData } from "./useData";

const button = twMerge(
  "bg-white dark:bg-neutral-950 text-black dark:text-white",
  "hover:bg-neutral-100 dark:hover:bg-neutral-900",
  "rounded",
  "h-8",
  "px-[3px]",
  "w-fit",
  "flex",
  "justify-center",
  "items-center"
);
const container = twMerge(
  "bg-neutral-100 dark:bg-neutral-900 text-black dark:text-white",
  "hover:bg-neutral-200 dark:hover:bg-neutral-800",
  "rounded",
  "shadow-lg",
  "dark:shadow-none",
  "p-2",
  "flex",
  "gap-x-1",
  "items-center"
);

const createDragContainerContent = () => {
  const container = document.createElement("div");

  return container;
};

const data = useData();

export const DragHandle = BaseDragHandle.configure({
  tippyOptions: {
    placement: "left",
  },
  render() {
    const div = document.createElement("div");
    const plus = document.createElement("button");
    const drag = document.createElement("button");
    const plusContainer = document.createElement("div");
    const dragContainer = createDragContainerContent();
    const plusP = document.createElement("p");

    div.classList.add("flex", "gap-x-1", "h-10", "items-center");
    plus.classList.add(...button.split(" "));
    drag.classList.add(...button.split(" "));
    plusContainer.classList.add(...container.split(" "));
    dragContainer.classList.add(...container.split(" "));
    plusP.classList.add("text-xs", "font-semibold", "p-0", "m-0");

    plus.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="size-4"><path d="M8.75 3.75a.75.75 0 0 0-1.5 0v3.5h-3.5a.75.75 0 0 0 0 1.5h3.5v3.5a.75.75 0 0 0 1.5 0v-3.5h3.5a.75.75 0 0 0 0-1.5h-3.5v-3.5Z" /></svg>`;
    drag.innerHTML = `<svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M5.5 2C5.69698 2 5.89204 2.0388 6.07403 2.11418C6.25601 2.18956 6.42137 2.30005 6.56066 2.43934C6.69995 2.57863 6.81044 2.74399 6.88582 2.92597C6.9612 3.10796 7 3.30302 7 3.5C7 3.69698 6.9612 3.89204 6.88582 4.07403C6.81044 4.25601 6.69995 4.42137 6.56066 4.56066C6.42137 4.69995 6.25601 4.81044 6.07403 4.88582C5.89204 4.9612 5.69698 5 5.5 5C5.10218 5 4.72064 4.84196 4.43934 4.56066C4.15804 4.27936 4 3.89782 4 3.5C4 3.10218 4.15804 2.72064 4.43934 2.43934C4.72064 2.15804 5.10218 2 5.5 2ZM5.5 6.5C5.89782 6.5 6.27936 6.65804 6.56066 6.93934C6.84196 7.22064 7 7.60218 7 8C7 8.39782 6.84196 8.77936 6.56066 9.06066C6.27936 9.34196 5.89782 9.5 5.5 9.5C5.10218 9.5 4.72064 9.34196 4.43934 9.06066C4.15804 8.77936 4 8.39782 4 8C4 7.60218 4.15804 7.22064 4.43934 6.93934C4.72064 6.65804 5.10218 6.5 5.5 6.5ZM7 12.5C7 12.1022 6.84196 11.7206 6.56066 11.4393C6.27936 11.158 5.89782 11 5.5 11C5.10218 11 4.72064 11.158 4.43934 11.4393C4.15804 11.7206 4 12.1022 4 12.5C4 12.8978 4.15804 13.2794 4.43934 13.5607C4.72064 13.842 5.10218 14 5.5 14C5.89782 14 6.27936 13.842 6.56066 13.5607C6.84196 13.2794 7 12.8978 7 12.5Z" fill="black"/>
            <path d="M10.5 2C10.697 2 10.892 2.0388 11.074 2.11418C11.256 2.18956 11.4214 2.30005 11.5607 2.43934C11.6999 2.57863 11.8104 2.74399 11.8858 2.92597C11.9612 3.10796 12 3.30302 12 3.5C12 3.69698 11.9612 3.89204 11.8858 4.07403C11.8104 4.25601 11.6999 4.42137 11.5607 4.56066C11.4214 4.69995 11.256 4.81044 11.074 4.88582C10.892 4.9612 10.697 5 10.5 5C10.1022 5 9.72064 4.84196 9.43934 4.56066C9.15804 4.27936 9 3.89782 9 3.5C9 3.10218 9.15804 2.72064 9.43934 2.43934C9.72064 2.15804 10.1022 2 10.5 2ZM10.5 6.5C10.8978 6.5 11.2794 6.65804 11.5607 6.93934C11.842 7.22064 12 7.60218 12 8C12 8.39782 11.842 8.77936 11.5607 9.06066C11.2794 9.34196 10.8978 9.5 10.5 9.5C10.1022 9.5 9.72064 9.34196 9.43934 9.06066C9.15804 8.77936 9 8.39782 9 8C9 7.60218 9.15804 7.22064 9.43934 6.93934C9.72064 6.65804 10.1022 6.5 10.5 6.5ZM12 12.5C12 12.1022 11.842 11.7206 11.5607 11.4393C11.2794 11.158 10.8978 11 10.5 11C10.1022 11 9.72064 11.158 9.43934 11.4393C9.15804 11.7206 9 12.1022 9 12.5C9 12.8978 9.15804 13.2794 9.43934 13.5607C9.72064 13.842 10.1022 14 10.5 14C10.8978 14 11.2794 13.842 11.5607 13.5607C11.842 13.2794 12 12.8978 12 12.5Z" fill="black"/>
            </svg>
            `;
    plusP.innerText = "Add a new block";
    plusContainer.appendChild(plusP);

    // const plusTippy = tippy(plus, {
    //   content: plusContainer,
    //   placement: 'top',
    //   allowHTML: true,
    // });
    // const dragTippy = tippy(drag, {
    //   content: dragContainer,
    //   placement: 'right-start',
    //   trigger: 'click',
    //   allowHTML: true,
    // });

    div.appendChild(plus);
    div.appendChild(drag);

    plus.addEventListener("click", () => {
      console.log("Add a new block");
      // editor.chain().command(({ tr, dispatch }) => {
      //   console.log(tr);
      //   if (!dispatch) {
      //     return false;
      //   }
      //   return true;
      // });
    });

    return div;
  },
  onNodeChange: ({ node, editor }) => {
    data.handleNodeChange({ node, pos: editor.state.selection.from });
  },
});

export default DragHandle;
