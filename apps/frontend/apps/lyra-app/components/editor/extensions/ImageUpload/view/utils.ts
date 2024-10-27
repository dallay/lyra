import { ref, onMounted, onUnmounted } from 'vue'
import { useToast } from '@/components/ui/toast/use-toast'

export function useUploader(onUpload: (url: string) => void) {
  const loading = ref(false)
  const { toast } = useToast()

  const uploadFile = async (file: File) => {
    const { $api } = useNuxtApp();

    loading.value = true
    try {
      const url = await $api.bucket.uploadFile(file)
      onUpload(url)
      toast({
        title: 'Success',
        description: 'File uploaded successfully'
      });
    } catch (errPayload: any) {
      const error = errPayload?.response?.data?.error || 'Something went wrong'
      toast({
        title: 'Error',
        description: error,
      });
    } finally {
      loading.value = false
    }
  }

  return { loading, uploadFile }
}

export function useFileUpload() {
  const fileInputRef = ref<HTMLInputElement | null>(null)

  const handleUploadClick = () => {
    fileInputRef.value?.click()
  }

  return { fileInputRef, handleUploadClick }
}

export function useDropZone(uploader: (file: File) => void) {
  const isDragging = ref(false)
  const draggedInside = ref(false)

  const dragStartHandler = () => {
    isDragging.value = true
  }

  const dragEndHandler = () => {
    isDragging.value = false
  }

  onMounted(() => {
    document.body.addEventListener('dragstart', dragStartHandler)
    document.body.addEventListener('dragend', dragEndHandler)
  })

  onUnmounted(() => {
    document.body.removeEventListener('dragstart', dragStartHandler)
    document.body.removeEventListener('dragend', dragEndHandler)
  })

  const onDrop = (e: DragEvent) => {
    draggedInside.value = false
    if (!e.dataTransfer || e.dataTransfer.files.length === 0) return

    const files = Array.from(e.dataTransfer.files).filter(file => file.type.includes('image'))
    if (files.length > 0) {
      e.preventDefault()
      uploader(files[0])
    }
  }

  const onDragEnter = () => {
    draggedInside.value = true
  }

  const onDragLeave = () => {
    draggedInside.value = false
  }

  return { isDragging, draggedInside, onDrop, onDragEnter, onDragLeave }
}
