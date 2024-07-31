interface Document {
    abstract void getDocumentInfo();
}

class WordDocument implements Document {
    @Override
    public void getDocumentInfo() {
        System.out.println("File Type: MS Word");
        System.out.println("File Extension: .docx");
        System.out.println();
    }
}

class PdfDocument implements Document {
    @Override
    public void getDocumentInfo() {
        System.out.println("File Type: PDF");
        System.out.println("File Extension: .pdf");
        System.out.println();
    }
}
class ExcelDocument implements Document {
    @Override
    public void getDocumentInfo() {
        System.out.println("File Type: MS Excel");
        System.out.println("File Extension: .xlsx");
        System.out.println();
    }
}

abstract class DocumentFactory {
    abstract Document createDocument();

    void getInfo() {
        Document document = createDocument();
        document.getDocumentInfo();
    }
}

class WordDocumentFactory extends DocumentFactory {
    public Document createDocument() {
        return new WordDocument();
    }
}

class PdfDocumentFactory extends DocumentFactory {
    public Document createDocument() {
        return new PdfDocument();
    }
}

class ExcelDocumentFactory extends DocumentFactory {
    public Document createDocument() {
        return new ExcelDocument();
    }
}

public class Ex2FactoryMethodPatternExample {
    public static void main(String[] args) {
        DocumentFactory df = new WordDocumentFactory();
        df.getInfo();

        df = new PdfDocumentFactory();
        df.getInfo();

        df = new ExcelDocumentFactory();
        df.getInfo();
    }
}
