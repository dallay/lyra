export default class UpdateFormRequest {
  constructor(
    public readonly name: string,
    public readonly header: string,
    public readonly description: string,
    public readonly inputPlaceholder: string,
    public readonly buttonText: string,
    public readonly buttonColor: string,
    public readonly backgroundColor: string,
    public readonly textColor: string,
    public readonly buttonTextColor: string,
    public readonly organizationId: string,
  ) {
    this.name = name;
    this.header = header;
    this.description = description;
    this.inputPlaceholder = inputPlaceholder;
    this.buttonText = buttonText;
    this.buttonColor = buttonColor;
    this.backgroundColor = backgroundColor;
    this.textColor = textColor;
    this.buttonTextColor = buttonTextColor;
    this.organizationId = organizationId;
  }
}
