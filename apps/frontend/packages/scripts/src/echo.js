import chalk from 'chalk';

const styles = {
  error: (text) => chalk.bold.red.bgGray.bold(text),
  warning: (text) => chalk.hex('#FFA500').bgGray.bold(text), // Orange color
  info: (text) => chalk.blue.bgGray.bold(text),
  success: (text) => chalk.green.bgGray.bold(text),
  underline: (text) => chalk.underline(text),
  bold: (text) => chalk.bold(text),
  dim: (text) => chalk.dim(text),
  inverse: (text) => chalk.inverse(text),
  hidden: (text) => chalk.hidden(text),
  strikethrough: (text) => chalk.strikethrough(text),
  visible: (text) => chalk.visible(text),
  rgb: (r, g, b, text) => chalk.rgb(r, g, b)(text),
  hex: (color, text) => chalk.hex(color)(text),
  bgRgb: (r, g, b, text) => chalk.bgRgb(r, g, b)(text),
  bgHex: (color, text) => chalk.bgHex(color)(text),
};

// Get the command line arguments
const args = process.argv.slice(2);
const message = args[0];
let style = args[1];

// Verify if a message was provided
if (!message) {
  console.error(chalk.red('Error: Debes proporcionar un mensaje.'));
  process.exit(1);
}
// If no style was provided, use the default style
if (!style) {
  style = 'success';
}

// Verify if a valid style was provided
if (!styles[style]) {
  console.error(chalk.red('Error: Estilo no válido.'));
  process.exit(1);
}

// Print the message with the specified style
console.log(styles[style](message));
